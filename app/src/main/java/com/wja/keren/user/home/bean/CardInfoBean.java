package com.wja.keren.user.home.bean;

import java.io.Serializable;
import java.util.List;

public class CardInfoBean implements Serializable{
    private int code;
    private List<CardDetailed> data;
    private String msg;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(List<CardDetailed> data) {
        this.data = data;
    }

    public List<CardDetailed> getData() {
        return data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public  static class CardDetailed implements Serializable {
        private String name;
        private int status;
        private String frame_code;
        private String color;
        private int is_bind;
        private int id;
        private String sn_code;
        private String engine_code;
        private String imei;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getFrame_code() {
            return frame_code;
        }

        public void setFrame_code(String frame_code) {
            this.frame_code = frame_code;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getIs_bind() {
            return is_bind;
        }

        public void setIs_bind(int is_bind) {
            this.is_bind = is_bind;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSn_code() {
            return sn_code;
        }

        public void setSn_code(String sn_code) {
            this.sn_code = sn_code;
        }

        public String getEngine_code() {
            return engine_code;
        }

        public void setEngine_code(String engine_code) {
            this.engine_code = engine_code;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }
    }

}


