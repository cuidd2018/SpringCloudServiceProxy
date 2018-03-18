package com.zxj.cloud_service_proxy_core.config;

import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;

import java.util.concurrent.ExecutorService;


/**
 * @author zhuxiujie
 * @since 2018/3/18
 */

public class ProxyCoreConfig {

    private static ProxyCoreConfig singleton = null;

    public interface ProxyCoreConfigBuilder {

        /**
         * 构建序列化 编码器
         * @return
         */
        Encoder buildEncoder();

        /**
         * 构建序列化 解码器
         * @return
         */
        Decoder buildDecoder();

        /**
         * 自定义构建用于 服务层的线程池
         * 会在服务层用到的时候调用构建
         * @return
         */
        ExecutorService buildExecutorService();
    }

    ProxyCoreConfig(ProxyCoreConfigBuilder proxyCoreConfigBuilder) {
        this.proxyCoreConfigBuilder = proxyCoreConfigBuilder;
    }

    private ProxyCoreConfigBuilder proxyCoreConfigBuilder = null;

    private Decoder decoder = null;

    private Encoder encoder = null;

    private ExecutorService executorService = null;

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Decoder getDecoder() {
        if (decoder == null) decoder = proxyCoreConfigBuilder.buildDecoder();
        return decoder;
    }

    public Encoder getEncoder() {
        if (encoder == null) encoder = proxyCoreConfigBuilder.buildEncoder();
        return encoder;
    }

    public ExecutorService getExecutorService() {
        if (executorService == null) executorService = proxyCoreConfigBuilder.buildExecutorService();
        return executorService;
    }

    /**
     * 取单例
     *
     * @return
     */
    public static ProxyCoreConfig getSingleton() {
        if (singleton == null) singleton = DefaultProxyCoreConfigProvider.getDefault();
        return singleton;
    }

    /**
     * 自定义配置
     *
     * @param singleton
     */
    public static void setSingleton(ProxyCoreConfig singleton) {
        ProxyCoreConfig.singleton = singleton;
    }
}
