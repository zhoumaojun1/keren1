package com.wja.keren.user.home.bean;

public class GetVerifyCodeBean {

    private int code;
    private VerifyCodeBean data;
    private String msg;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setData(VerifyCodeBean data) {
        this.data = data;
    }
    public VerifyCodeBean getData() {
        return data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }


}
