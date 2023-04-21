package com.wja.keren.user.home.bean;

import java.io.Serializable;

public class VerifyCodeBean implements Serializable {
    private int code;

    private String msg;
    private VerifyCode verifyCode;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public VerifyCode getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(VerifyCode verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class VerifyCode implements Serializable{

      private   boolean status;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}
