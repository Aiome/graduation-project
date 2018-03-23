package top.aiome.service.seckill.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import top.aiome.Constants;
import top.aiome.dao.seckill.entity.Seckill;
import top.aiome.service.seckill.service.interfaces.ISeckillSV;

/**
 * Created by mahongyan on 2018/3/22
 * 预处理阶段，把不必要的请求直接驳回，必要的请求添加到队列中进入下一阶段.
 */
@Service
public class PreProcessor {
    private static Logger logger = LoggerFactory.getLogger(PreProcessor.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ISeckillSV seckillSVImpl;

    private static String generateRedisKey(Integer seckillId){
        return "seckill_" + seckillId.toString();
    }
    private int checkReminds(Integer seckillId) {
        //0:无库存 1：有库存 2：秒杀id不存在
        int reminds = 1;
        long t1 = System.currentTimeMillis();
        //从Redis检查数据，不必完全严格检查
        String count = redisTemplate.opsForValue().get(generateRedisKey(seckillId));
        long t2 = System.currentTimeMillis();

        if (null != count){
            reminds = Integer.parseInt(count) < 1 ? 0 : 1;
            logger.info("通过Redis查询库存情况:" + (t2 - t1));
        }else {
            //如果redis无缓存则查mysql
            long t3 = System.currentTimeMillis();
            Seckill seckillById = seckillSVImpl.getSeckillById(seckillId);
            long t4 = System.currentTimeMillis();
            logger.info("通过Mysql查询库存情况:" + (t4 - t3));
            if (seckillById == null){
                return 2;
            }
            reminds = seckillById.getSeckillCount() < 1 ?  0 : 1;
            //存入redis
            redisTemplate.opsForValue().set(generateRedisKey(seckillId),
                    seckillById.getSeckillCount().toString(),60000, TimeUnit.MILLISECONDS);
        }
        return reminds;
    }
    /**
     * 每一个HTTP请求都要经过该预处理.
     */
    public int preProcess(SeckillInfo info) {
        int result = checkReminds(info.getSeckillId());
        if (1 == result) {
            // 一个并发的队列
            RequestQueue.queue.add(info);
//            logger.info("秒杀队列情况：" + RequestQueue.queue.size()+"");

            return Constants.Seckill.SECKILL_WAIT;
        }else if (2 == result){
            return Constants.Seckill.SECKILL_ILLEGAL_ID;
        }

        return Constants.Seckill.SECKILL_FAIL;
    }
}
