package com.zxj.cloud_service_proxy_core.util.invoke;

import com.zxj.cloud_service_proxy_core.config.RestTempletConfig;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxiujie
 * @since 2018/2/19
 */

public class DefaultRestTempleteProvider {


    /**
     * 默认使用Okhttp3实现RestTemplete
     *
     * @param restTempletConfig
     * @return
     */
    @Deprecated
    public static RestTemplate restTemplate(RestTempletConfig restTempletConfig) {

        ConnectionPool connectionPool = new ConnectionPool(restTempletConfig.getMaxIdleConnections(),restTempletConfig.getKeepAliveDuration(),restTempletConfig.getTimeUnit());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(restTempletConfig.isRetryOnConnectionFailure());
        builder.readTimeout(restTempletConfig.getReadTimeout(), restTempletConfig.getTimeUnit());
        builder.connectTimeout(restTempletConfig.getConnectTimeout(), restTempletConfig.getTimeUnit());
        builder.connectionPool(connectionPool);
        OkHttpClient okHttpClient = builder.build();
        // httpClient连接配置，底层是配置RequestConfig
        OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        okHttp3ClientHttpRequestFactory.setConnectTimeout((int) restTempletConfig.getTimeUnit().toMillis(restTempletConfig.getConnectTimeout()));
        okHttp3ClientHttpRequestFactory.setReadTimeout((int) restTempletConfig.getTimeUnit().toMillis(restTempletConfig.getReadTimeout()));
        okHttp3ClientHttpRequestFactory.setWriteTimeout((int) restTempletConfig.getTimeUnit().toMillis(restTempletConfig.getWriteTimeout()));
        // 添加内容转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        //messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        //messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(new ByteArrayHttpMessageConverter());//byte[] 必须添加
        RestTemplate restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(okHttp3ClientHttpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate;
    }
}
