package com.wja.keren.user.home.bean;

import java.io.Serializable;

public class BaseBean implements Serializable {

    private int code;
    private String message;

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Data{

    }
}
