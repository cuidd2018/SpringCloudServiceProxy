package com.zxj.cloud_service_proxy_core.variable;

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
}
