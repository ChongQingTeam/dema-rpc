package com.dema.registry.service;

import com.dema.metadata.report.ServiceMeta;

import java.util.List;

public interface RegistryService {

    void register(ServiceMeta serviceMeta) throws Exception;

    void destory();

    ServiceMeta discovery(String serviceName) throws Exception;
}
