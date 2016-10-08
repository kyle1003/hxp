package com.hxp.filter.log;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2016/9/4.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String desc();
}
