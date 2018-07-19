package com.example.demo_service_interface.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基本对象无需实现 java.io.Serializable
 * 复杂对象必须实现 java.io.Serializable 序列化接口
 */
public class DemoVO implements Serializable {

    private String _key;

    private String name;

    private TestVO testVO;

    private List<TestVO> testVOs;

    private Map<String,TestVO> testVOMap;

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

    public void setTestVOMap(Map<String, TestVO> testVOMap) {
        this.testVOMap = testVOMap;
    }

    public void setTestVOs(List<TestVO> testVOs) {
        this.testVOs = testVOs;
    }

    public List<TestVO> getTestVOs() {
        return testVOs;
    }

    public Map<String, TestVO> getTestVOMap() {
        return testVOMap;
    }
}
