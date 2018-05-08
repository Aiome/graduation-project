package top.aiome.service.groupPurchase.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import top.aiome.dao.seckill.SeckillMapper;
import top.aiome.dao.seckill.entity.Seckill;
import top.aiome.dao.seckill.entity.SeckillExample;
import top.aiome.service.groupPurchase.service.interfaces.GroupInfo;
import top.aiome.service.groupPurchase.service.interfaces.IGroupPurchaseSV;

/**
 * Created by mahongyan on 2018/3/24
 */
public class GroupPurchaseSVImpl implements IGroupPurchaseSV{

    private static final Logger log = LoggerFactory.getLogger(GroupPurchaseSVImpl.class);
    private static final List<GroupInfo> result =  null;

    @Resource //自动注入
    private SeckillMapper groupPurchaseMapper;

    @Override
    public List<GroupInfo> getGroupInfo(int itemId) {
        log.info("开始根据商品Id获取团购信息息");

        //设置查询数据库条件：根据开团剩余时间进行升序排序
        SeckillExample example = new SeckillExample();
        example.setOrderByClause("ORDER BY EDN ASC");

        //设置查询数据库条件：根据剩余组团人数排序
        SeckillExample.Criteria criteria = example.createCriteria();
        criteria.andSeckillCountGreaterThanOrEqualTo(5)
                .andEndLessThanOrEqualTo(new Date());

        //查询数据库开团信息
        try {
            //调用数据层 通过Mybatis进行SQL拼装并查询
            List<Seckill> result = groupPurchaseMapper.selectByExample(example);
        } catch (Exception e){
            log.error("获取团购信息异常！",e);
        }

        return result;
    }

    @Override
    @Transactional //
    public void buyByGroupId(int groupId) {
        //通过团购id进行判断是否可以成团
        SeckillExample example = new SeckillExample();
        SeckillExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(groupId);
        Seckill seckills = groupPurchaseMapper.selectByPrimaryKey(groupId);

        if (seckills == null){
            //如果团购信息不存在（新开团）插入一个团购信息
            Seckill seckill = new Seckill();
            seckill.setItemId(1);
            seckill.setSeckillCount(5);
            seckill.setSeckillSwitch(1);
            groupPurchaseMapper.insert(seckill);
        }

        if (seckills.getSeckillSwitch() == 1 && seckills.getSeckillCount() >= 1){
            //成员未满，可以参团
            Seckill seckill = new Seckill();
            seckill.setId(groupId);
            seckill.setSeckillSwitch(0);
            seckill.setSeckillCount( seckills.getSeckillCount() - 1);
            groupPurchaseMapper.updateByPrimaryKey(seckill);
        }
        //团购已满，不做处理
    }
}
