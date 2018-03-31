package top.aiome.service.seckill.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import top.aiome.dao.order.entity.Order;
import top.aiome.dao.seckill.entity.Seckill;
import top.aiome.service.order.service.interfaces.IOrderSV;
import top.aiome.service.seckill.service.interfaces.ISeckillSV;
import top.aiome.util.Constants;

/**
 * Created by mahongyan on 2018/3/22
 * 预处理阶段，把不必要的请求直接驳回，必要的请求添加到队列中进入下一阶段.
 */
@Service
public class Processor {
    private static Logger logger = LoggerFactory.getLogger(Processor.class);

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private ISeckillSV seckillSVImpl;

    @Autowired
    private IOrderSV orderSVImpl;

    /**
     * 生成秒杀Redis Key
     * @param seckillId
     * @return
     */
    private static String generateRedisKey(Integer seckillId){
        return "seckill_" + seckillId.toString();
    }

    /**
     * 生成秒杀库存锁
     * @param seckillId
     * @return
     */
    private static String generateRedisKeyLoc(Integer seckillId){
        return "seckill_loc_" + seckillId.toString();
    }

    /**
     * 获取库存情况，将Mysql库存刷到Redis
     * @param seckillId
     * @param m  0:无库存 1：有库存 2：秒杀id不存在
     * @return
     */
    private int checkReminds(Integer seckillId,Long m) {
        int reminds = 1;    //从Redis检查数据，不必完全严格检查
        boolean hasKey = redisTemplate.hasKey(generateRedisKey(seckillId));
        boolean hasKeyLoc = redisTemplate.hasKey(generateRedisKeyLoc(seckillId));
        //redis库存已经初始化
        if (hasKey){
            Long count = redisTemplate.opsForList().size(generateRedisKey(seckillId));
            reminds = count < 1 ? 0 : 1;
            logger.info(m + "---redis 库存情况:" + count);
        }else if(!hasKeyLoc){ //如果redis库存未初始化并且没有锁
            //校验商品有效性
            Seckill seckillById = seckillSVImpl.getSeckillById(seckillId);
            logger.info(m + "---mysql 库存情况:" + seckillById.getSeckillCount());
            if (seckillById == null){ return 2; }
            reminds = seckillById.getSeckillCount() < 1 ?  0 : 1;
            //将库存存入redis  并且线程同步
            synchronized (this){
                boolean hasLoc = redisTemplate.hasKey(generateRedisKeyLoc(seckillId));
                if (hasLoc){
                    return redisTemplate.opsForList().size(generateRedisKey(seckillId)) > 0 ? 1 : 0;
                }
                //redis加锁并且将库存刷入
                redisTemplate.opsForList().leftPush(generateRedisKeyLoc(seckillId),1);
                List<Object> objects = redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override//redis管道刷库存数据
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        connection.openPipeline();
                        for (int i = 1; i <= seckillById.getSeckillCount(); i ++){
                            redisTemplate.opsForList().leftPush(generateRedisKey(seckillId),i);
                        }
                        connection.closePipeline();
                        return null;
                    }
                });
            }
        }else if (hasKeyLoc){//如果redis没有库存信息但是又锁
            reminds = 0;
        }
        return reminds;
    }

    /**
     * 减库存操作，Redis库存扣减成功再操作Mysql库存
     * @param info
     * @return
     */
    public int preProcess(SeckillInfo info){
        Long m = System.currentTimeMillis();
        int result = checkReminds(info.getSeckillId(),m);
        if (1 == result) {//有库存
             //原子操作扣减Redis缓存库存
             Integer value = redisTemplate.opsForList().rightPop(generateRedisKey(info.getSeckillId()));
             logger.info(m + "---减redis库存返回值：" + value);
            //开启事务进行减库存下单操作
            if (null != value && value >= 0){
                logger.info( m + "---redis 出队：successful");
                Seckill seckill = new Seckill();
                seckill.setId(info.getSeckillId());
                //扣库存
                boolean reduceInventory = seckillSVImpl.reduceInventory(seckill);
                if (!reduceInventory){  logger.info( m + "---扣减库存：失败" );    }
                logger.info( m + "---扣减库存：成功" );
                //下单
                Order order = new Order();
                order.setTypeId(info.getSeckillId());
                order.setCreateTime(new Date());
                order.setUserId(info.getUserId());
                int insertOrder = orderSVImpl.insertOrder(order);
                if (insertOrder < 1){
                    logger.info( m + "---下单操作：失败" );
                    throw new RuntimeException("下单操作：失败" );
                }
                logger.info( m + "---下单操作：成功" );
                return  Constants.Seckill.SECKILL_SUCCESS;
            }
            return Constants.Seckill.SECKILL_FAIL;
        }else if (2 == result){//秒杀不存在
            return Constants.Seckill.SECKILL_ILLEGAL_ID;
        }else if (0 == result){//无库存
            logger.info(m + "---无库存");
            return Constants.Seckill.SECKILL_FAIL;
        }
        return Constants.Seckill.SECKILL_FAIL;
    }
}
