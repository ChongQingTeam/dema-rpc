package com.dema.registry.service;

import com.dema.metadata.report.ServiceMeta;

import java.util.List;

public class NacosRegistryServiceImpl implements RegistryService{

    @Override
    public void register(ServiceMeta serviceMeta) {

    }

    @Override
    public void destory() {

    }

    @Override
    public List<ServiceMeta> discovery() {
        return null;
    }

    public NacosRegistryServiceImpl(String registryAddrress) {
    }
}
