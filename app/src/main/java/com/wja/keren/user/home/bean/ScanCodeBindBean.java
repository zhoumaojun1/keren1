package com.wja.keren.user.home.bean;

import java.io.Serializable;

public class ScanCodeBindBean implements Serializable {
    private String code;
    private ScanCodeBind data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ScanCodeBind getData() {
        return data;
    }

    public void setData(ScanCodeBind data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ScanCodeBind implements Serializable{
        private String image;
        private String frame_code;
        private String name;
        private int is_bind;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFrame_code() {
            return frame_code;
        }

        public void setFrame_code(String frame_code) {
            this.frame_code = frame_code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_bind() {
            return is_bind;
        }

        public void setIs_bind(int is_bind) {
            this.is_bind = is_bind;
        }
    }
}
