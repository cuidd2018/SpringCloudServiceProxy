package com.zxj.cloud_service_proxy_core.util.invoke.config;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuxiujie
 * @since 2018/2/19
 */

public class RestTempletConfig implements Serializable {

    private TimeUnit timeUnit=TimeUnit.SECONDS;
    private int connectTimeout = 35;//s
    private int readTimeout=35;//s
    private int writeTimeout=35;//s
    private boolean retryOnConnectionFailure=false;//启用失败重试
    private int maxIdleConnections=5;
    private long keepAliveDuration=5;


    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public RestTempletConfig setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public RestTempletConfig setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public RestTempletConfig setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public RestTempletConfig setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public boolean isRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }

    public RestTempletConfig setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
        this.retryOnConnectionFailure = retryOnConnectionFailure;
        return this;
    }

    public RestTempletConfig setKeepAliveDuration(long keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
        return this;
    }

    public RestTempletConfig setMaxIdleConnections(int maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
        return this;
    }

    public int getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public long getKeepAliveDuration() {
        return keepAliveDuration;
    }
}
