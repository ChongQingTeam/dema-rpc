package com.dema.core.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dema.rpc")
public class RpcDemaProperties {

    private int servicePort;

    private String registryType;

    private String registryAddress;
}
