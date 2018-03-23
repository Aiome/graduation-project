package top.aiome;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import top.aiome.redis.aspect.RequestLimit;
import top.aiome.util.HttpRequestUtil;

;

/**
 * Created by mahongyan on 2018/3/21
 */
@Aspect
@Component
public class RequestLimitAspect {
    private static Logger logger = LoggerFactory.getLogger(RequestLimitAspect.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Before("execution(public * top.aiome..*.*(..)) && @annotation(limit)")
    public void requestLimit(JoinPoint joinpoint, RequestLimit limit) {
        logger.info("---------------------------AOP-----------------------------------");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String ip = HttpRequestUtil.getIpAddr(request);
        String url = request.getRequestURL().toString();
        String key = "req_limit_".concat(url).concat(ip);

        //加1后看看值
        long count = redisTemplate.opsForValue().increment(key, 1);
        //刚创建
        if (count == 1) {
            //设置1分钟过期
            redisTemplate.expire(key, limit.time(), TimeUnit.MILLISECONDS);
        }
        if (count > limit.count()) {
            logger.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + "]");
            throw new RuntimeException("超出访问次数限制");
        }
        logger.info("---------------------------AOP-----------------------------------");
    }
}
