package com.zxj.cloud_service_proxy_core.config;

import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;
import org.nustaq.serialization.FSTConfiguration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhuxiujie
 * @since 2018/3/18
 */

public class DefaultProxyCoreConfigProvider {

    private static ProxyCoreConfig proxyCoreConfig = null;

    public static ProxyCoreConfig getDefault() {
        if (proxyCoreConfig == null) {
            proxyCoreConfig = new ProxyCoreConfig(new ProxyCoreConfig.ProxyCoreConfigBuilder() {
                private FSTConfiguration fstConfiguration = FSTConfiguration.createDefaultConfiguration();

                @Override
                public Encoder buildEncoder() {
                    return object -> fstConfiguration.asByteArray(object);
                }

                @Override
                public Decoder buildDecoder() {
                    return bytes -> fstConfiguration.asObject(bytes);
                }

                @Override
                public ExecutorService buildExecutorService() {
                    ExecutorService defaultExecutor = Executors.newFixedThreadPool(200);
                    return defaultExecutor;
                }
            });
        }
        return proxyCoreConfig;
    }
}
