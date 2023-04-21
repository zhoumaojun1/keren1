package com.wja.keren.user.home.bean;

import java.io.Serializable;

public class UserSetCardBean implements Serializable {

    private int code;
    private String msg;
    private UseSetCard data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UseSetCard getData() {
        return data;
    }

    public void setData(UseSetCard data) {
        this.data = data;
    }

    public static class UseSetCard implements Serializable{
        private int id;
        private int user_id;
        private int face;//人脸识别 0 关闭 1开启
        private int fingerprint;//指纹登录 0 关闭 1开启
        private int vibration;//异常振动 0 关闭 1开启
        private int cut_power;//切断电源 0 关闭 1开启
        private int charge_monitor;//充电监测0 关闭 1开启
        private int guard_alarm;//防盗报警0 关闭 1开启
        private int fault_alarm;//故障消息0 关闭 1开启
        private long created_at;
        private long updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getFace() {
            return face;
        }

        public void setFace(int face) {
            this.face = face;
        }

        public int getFingerprint() {
            return fingerprint;
        }

        public void setFingerprint(int fingerprint) {
            this.fingerprint = fingerprint;
        }

        public int getVibration() {
            return vibration;
        }

        public void setVibration(int vibration) {
            this.vibration = vibration;
        }

        public int getCut_power() {
            return cut_power;
        }

        public void setCut_power(int cut_power) {
            this.cut_power = cut_power;
        }

        public int getCharge_monitor() {
            return charge_monitor;
        }

        public void setCharge_monitor(int charge_monitor) {
            this.charge_monitor = charge_monitor;
        }

        public int getGuard_alarm() {
            return guard_alarm;
        }

        public void setGuard_alarm(int guard_alarm) {
            this.guard_alarm = guard_alarm;
        }

        public int getFault_alarm() {
            return fault_alarm;
        }

        public void setFault_alarm(int fault_alarm) {
            this.fault_alarm = fault_alarm;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public long getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(long updated_at) {
            this.updated_at = updated_at;
        }
    }
}
