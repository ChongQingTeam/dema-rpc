package com.dema.registry.service;

import com.dema.metadata.report.ServiceMeta;

import java.util.List;

public interface RegistryService {

    void register(ServiceMeta serviceMeta);

    void destory();

    List<ServiceMeta> discovery();
}
