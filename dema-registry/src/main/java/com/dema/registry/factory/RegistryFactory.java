package com.dema.registry.factory;

import com.dema.registry.enums.RegistryType;
import com.dema.registry.service.*;

public class RegistryFactory {


    private static volatile RegistryService registryService;

    public static  RegistryService getRegistryServiceInstance(String registryAddress, RegistryType type){
        if (null == registryService) {
            synchronized (RegistryFactory.class) {
                if (null == registryService) {
                    switch (type) {
                        case ZOOKEEPER:
                            registryService = new ZookeeperRegistryServiceImpl(registryAddress);
                            break;
                        case EUREKA:
                            registryService = new EurekaRegistryServiceImpl(registryAddress);
                            break;
                        case REDIS:
                            registryService = new RedisRegistryServiceImpl(registryAddress);
                            break;
                        case NACOS:
                            registryService = new NacosRegistryServiceImpl(registryAddress);
                            break;
                    }
                }
            }
        }
        return registryService;
    }
}
