package com.zxj.cloud_service_proxy_core.react.mono;

public interface MonoProxyCallBack<T> {
    T accept() throws Exception;
}
