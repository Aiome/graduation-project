package top.aiome.redis.aspect;

/**
 * Created by mahongyan on 2018/3/21
 */


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
//最高优先级
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface  RequestLimit {
    /**
     * 允许访问的次数
     */
    int count() default 1;

    /**
     * 时间段，多少时间段内运行访问count次
     */
    long time() default 2000;
}
