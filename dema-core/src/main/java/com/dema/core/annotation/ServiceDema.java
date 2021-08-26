package com.dema.core.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Autowired
public @interface ServiceDema {
    /**
     * 名称
     */
    String name() default "";

    /**
     * 暴露的服务
     */
    String value() default "";


}
