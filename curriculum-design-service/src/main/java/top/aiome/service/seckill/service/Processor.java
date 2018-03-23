package top.aiome.service.seckill.service;

/**
 * Created by mahongyan on 2018/3/22
 */
public class Processor {
    /**
     * 发送秒杀事务到数据库队列.
     */
    private static void kill(BidInfo info) {
        DB.bids.add(info);
    }

    public static void process() {
        BidInfo info = new BidInfo(RequestQueue.queue.poll());
        if (info != null) {
            kill(info);
        }
    }
}
class BidInfo {
    BidInfo(SeckillInfo request) {

    }
}
