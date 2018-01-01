package com.zxj.cloud_service_proxy_core.variable;

import java.io.Serializable;

/**
 * Created by zhuxiujie on 2017/5/9.
 */
public interface Variable<T> extends Serializable{
    /**
     * 枚举常量名称
     *
     * @return
     */
    String getName();

    /**
     * 枚举常量值
     *
     * @return
     */
    T getValue();
}
