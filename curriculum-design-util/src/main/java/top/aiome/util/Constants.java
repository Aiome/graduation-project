package top.aiome.util;

/**
 * Created by mahongyan on 2018/3/23
 */
public class Constants {
    public static class Seckill{
        //抢购成功
        public static final int SECKILL_SUCCESS = 1000;
        //请求频繁
        public static final int SECKILL_FREQUENT = 1001;
        //排队成功，等待抢购结果
        public static final int SECKILL_WAIT = 1002;
        //抢购失败,无库存
        public static final int SECKILL_FAIL = 1100;
        //无该抢购id
        public static final int SECKILL_ILLEGAL_ID = 1101;
    }
}
