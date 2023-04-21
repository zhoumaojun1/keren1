package com.wja.keren.user.home.bean;

import java.io.Serializable;

public class AgreeShareCardBean implements Serializable {

    private int code;
    private String msg;

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
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public static class Data{

    }
}
