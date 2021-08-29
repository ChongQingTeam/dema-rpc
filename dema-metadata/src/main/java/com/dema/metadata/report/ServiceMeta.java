package com.dema.metadata.report;

import lombok.Data;

@Data
public class ServiceMeta {
    /**
     * 地址
     */
    private String serviceAddr;
    /**
     * 端口
     */
    private int servicePort;
    /**
     * 名称
     */
    private String name;

    /**
     * 暴露的服务
     */
    private String value;

    /**
     * 接口版本
     */
    private String version;
}
