package com.zxj.cloud_service_proxy_core.util.enums;

import java.lang.annotation.*;


/**
 * 扫描枚举表
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EnableScanEnumTable {
}
