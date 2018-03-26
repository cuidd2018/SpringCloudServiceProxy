package com.example.demo_service_interface.vo;

import java.io.Serializable;

/**
 * 基本对象无需实现 java.io.Serializable
 * 复杂对象必须实现 java.io.Serializable 序列化接口
 */
public class DemoVO implements Serializable {

    private String _key;

    private String name;

    private TestVO testVO;

    public void setTestVO(TestVO testVO) {
        this.testVO = testVO;
    }

    public TestVO getTestVO() {
        return testVO;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void set_key(String _key) {
        this._key = _key;
    }

    public String get_key() {
        return _key;
    }
}
