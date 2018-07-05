package com.zxj.cloud_service_proxy_core.react.mono;

import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.function.Consumer;

public class ServiceProxyMono<T> implements Consumer<MonoSink<T>> {

    private MonoProxyCallBack<T> success;

    @Override
    public void accept(MonoSink<T> tMonoSink) {
        try {
            tMonoSink.success(success.accept());
        } catch (Exception e) {
            tMonoSink.error(e);
        }
    }


    public ServiceProxyMono(MonoProxyCallBack<T> success) {
        this.success = success;
    }

    public static <T> ServiceProxyMono createServiceProxyMono(MonoProxyCallBack<T> success) {
        ServiceProxyMono platformMonoCallBack = new ServiceProxyMono<>(success);
        return platformMonoCallBack;
    }

    public static <T> Mono<T> createMono(MonoProxyCallBack<T> success) {
       return Mono.create(createServiceProxyMono(success));
    }

}
