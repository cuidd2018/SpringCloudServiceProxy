package com.example.demo_service_interface.vo;

import java.io.Serializable;

public class TestVO implements Serializable{
    private String test;

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }
}
