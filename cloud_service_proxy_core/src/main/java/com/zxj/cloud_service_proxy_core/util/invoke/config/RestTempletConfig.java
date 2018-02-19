package com.zxj.cloud_service_proxy_core.util.invoke.config;

import java.io.Serializable;

/**
 * @author zhuxiujie
 * @since 2018/2/19
 */

public class RestTempletConfig implements Serializable {

    private int retryCount = 0;
    private boolean requestSentRetryEnabled = false;
    private int connectTimeout = 3000;
    private int socketTimeout = 3000;
    private int poolingHttpClientConnection = 30;
    private int maxTotal = 1000;//总连接数
    private int defaultMaxPerRoute = 1000;//同路由并发数
    private int readTimeout = 5000;
    private int connectionRequestTimeout = 200;//连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isRequestSentRetryEnabled() {
        return requestSentRetryEnabled;
    }

    public void setRequestSentRetryEnabled(boolean requestSentRetryEnabled) {
        this.requestSentRetryEnabled = requestSentRetryEnabled;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setPoolingHttpClientConnection(int poolingHttpClientConnection) {
        this.poolingHttpClientConnection = poolingHttpClientConnection;
    }

    public int getPoolingHttpClientConnection() {
        return poolingHttpClientConnection;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }
}
