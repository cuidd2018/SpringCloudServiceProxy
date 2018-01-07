package com.example.demo_service_interface.vo;

import java.io.Serializable;

public class DemoVO implements Serializable {
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
}
