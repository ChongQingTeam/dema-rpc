package com.dema.registry.service;

import com.dema.metadata.report.ServiceMeta;

import java.util.List;

public class RedisRegistryServiceImpl implements RegistryService{
    @Override
    public void register(ServiceMeta serviceMeta) {

    }

    @Override
    public void destory() {

    }

    @Override
    public ServiceMeta discovery(String serviceName) {
        return null;
    }

    public RedisRegistryServiceImpl(String registryAddrress) {
    }
}
