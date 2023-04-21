package com.wja.keren.user.home.bean;

import java.io.Serializable;

public class CardLocationBean implements Serializable {
    private int code;
    private String msg;
    private CardLocation cardLocation;

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

    public CardLocation getCardLocation() {
        return cardLocation;
    }

    public void setCardLocation(CardLocation cardLocation) {
        this.cardLocation = cardLocation;
    }

    public static class CardLocation implements Serializable{

    private Number lat;
        private Number lng;

        public Number getLat() {
            return lat;
        }

        public void setLat(Number lat) {
            this.lat = lat;
        }

        public Number getLng() {
            return lng;
        }

        public void setLng(Number lng) {
            this.lng = lng;
        }
    }
}
