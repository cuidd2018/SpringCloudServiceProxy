package com.zxj.cloud_service_proxy_core.util.invoke;

import java.io.IOException;

/**
 * @author zhuxiujie
 * @since 2018/3/18
 */

public interface Decoder {

    Object decoder(byte[] bytes) throws IOException;
}
