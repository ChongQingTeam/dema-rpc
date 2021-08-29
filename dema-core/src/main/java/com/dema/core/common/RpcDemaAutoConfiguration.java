package com.dema.core.common;

import com.dema.core.executor.RpcDemaSpringExecutor;
import com.dema.registry.enums.RegistryType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(RpcDemaProperties.class)
public class RpcDemaAutoConfiguration {

    @Resource
    RpcDemaProperties rpcDemaProperties;

    @Bean
    RpcDemaSpringExecutor init(){
        return new RpcDemaSpringExecutor(rpcDemaProperties);
    }
}
