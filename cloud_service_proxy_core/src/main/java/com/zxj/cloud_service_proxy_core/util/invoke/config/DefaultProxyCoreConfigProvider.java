package com.zxj.cloud_service_proxy_core.util.invoke.config;

import org.nustaq.serialization.FSTConfiguration;

/**
 * @author zhuxiujie
 * @since 2018/3/18
 */

public class DefaultProxyCoreConfigProvider {

    private static FSTConfiguration fstConfiguration = FSTConfiguration.createDefaultConfiguration();

    public static ProxyCoreConfig getDefault() {
        ProxyCoreConfig proxyCoreConfig = new ProxyCoreConfig();
        proxyCoreConfig.setEncoder(object -> fstConfiguration.asByteArray(object));
        proxyCoreConfig.setDecoder(bytes -> fstConfiguration.asObject(bytes));
        return proxyCoreConfig;
    }
}
