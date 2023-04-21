package com.wja.keren.user.home.bean;

import java.io.Serializable;

public class BatteryBean implements Serializable {
    private int code;
    private  String msg;
    private  Battery  battery;

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

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    /**
     * 是否是电量值，如果不是，则是电压值，电压值需要根据电池厂商的算法进行换算成电量值
     */
    public static class Battery implements Serializable{

       boolean quantity;//为true 时表示电量，反之为电压
       int val;//

        public boolean isQuantity() {
            return quantity;
        }

        public void setQuantity(boolean quantity) {
            this.quantity = quantity;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }
}
