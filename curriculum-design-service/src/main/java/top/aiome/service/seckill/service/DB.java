package top.aiome.service.seckill.service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mahongyan on 2018/3/22
 * DB应该是数据库的唯一接口.
 */
public class DB {
    public static ArrayBlockingQueue<BidInfo> bids = new ArrayBlockingQueue<BidInfo>(10);
    public static boolean checkReminds() {
        //读mysql查询是否有剩余
        return true;
    }

    // 单线程操作
    public static void bid() {
        BidInfo info = bids.poll();
        while (bids.isEmpty()) {

        // insert into table Bids values(item_id, user_id, bid_date, other)

        // select count(id) from Bids where item_id = ?

        // 如果数据库商品数量大约总数，则标志秒杀已完成，设置标志位reminds = false.
            info = bids.poll();
        }
    }
}