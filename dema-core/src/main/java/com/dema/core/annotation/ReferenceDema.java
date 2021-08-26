package com.dema.core.annotation;

public @interface ReferenceDema {

    /**
     * 名称
     */
    String name() default "";

    /**
     * 暴露的服务
     */
    String value() default "";

    /**
     * 超时重试次数
     */
    int retries() default 1;

    /**
     * 超时时间
     */
    long timeout() default 1000;

    /**
     * 接口版本
     */
    String version() default "1.0.0";

    /**
     * 统一配置类
     */
    Class<?>[] configuration() default {};

    /**
     * 回调类
     */
    Class<?> fallback() default void.class;

}
