package top.aiome.service.seckill.service;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by mahongyan on 2018/3/22
 */

public class RequestQueue {
    public static ConcurrentLinkedQueue<SeckillInfo> queue =new ConcurrentLinkedQueue<SeckillInfo>();
}