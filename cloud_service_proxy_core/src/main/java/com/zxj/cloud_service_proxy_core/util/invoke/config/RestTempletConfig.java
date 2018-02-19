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


    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public boolean isRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }

    public void setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
        this.retryOnConnectionFailure = retryOnConnectionFailure;
    }
}
