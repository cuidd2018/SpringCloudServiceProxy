package com.zxj.cloud_service_proxy_core.util.invoke.config;

import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;


/**
 * @author zhuxiujie
 * @since 2018/3/18
 */

public class ProxyCoreConfig {

    private static ProxyCoreConfig proxyCoreConfig = null;

    private Decoder decoder = null;

    private Encoder encoder = null;

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public Decoder getDecoder() {
        return decoder;
    }

    public Encoder getEncoder() {
        return encoder;
    }

    public static ProxyCoreConfig getProxyCoreConfig() {
        if (proxyCoreConfig == null) proxyCoreConfig = DefaultProxyCoreConfigProvider.getDefault();
        return proxyCoreConfig;
    }

    public static void setProxyCoreConfig(ProxyCoreConfig proxyCoreConfig) {
        ProxyCoreConfig.proxyCoreConfig = proxyCoreConfig;
    }
}
