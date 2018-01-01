package com.example.demo_service_interface.vo;

import java.io.Serializable;

public class DemoVO implements Serializable {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
