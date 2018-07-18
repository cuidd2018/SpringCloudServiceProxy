package com.zxj.cloud_service_proxy_core.config;

import com.zxj.cloud_service_proxy_core.util.convert.ConvertUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;
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
                @Override
                public Encoder buildEncoder() {
                    return ConvertUtil.getEncoder();
                }

                @Override
                public Decoder buildDecoder() {
                    return ConvertUtil.getDecoder();
                }

                @Override
                public ExecutorService buildExecutorService() {
                    //cpu core,2*core +1
                    int cpuCores=Runtime.getRuntime().availableProcessors()*2+1;
                    ExecutorService defaultExecutor = Executors.newFixedThreadPool(cpuCores);
                    return defaultExecutor;
                }
            });
        }
        return proxyCoreConfig;
    }
}
