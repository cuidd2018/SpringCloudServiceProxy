package com.zxj.cloud_service_proxy_core.constant;

import java.io.Serializable;

/**
 * 常量接口
 * Created by zhuxiujie on 2017/5/9.
 */
public interface Constant<T> extends Serializable{
    /**
     * 名称
     *
     * @return
     */
    String getName();

    /**
     * 常量值
     *
     * @return
     */
    T getValue();


    /**
     * 枚举类:推荐只在枚举内部使用，禁止外部调用
     * @param name
     * @return
     */
    void setName(String name);

    /**
     * 枚举类:推荐只在枚举内部使用，禁止外部调用
     * @param value
     * @return
     */
    void setValue(T value);
}
