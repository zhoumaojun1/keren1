package com.wja.keren.user.home.bean;

import java.io.Serializable;
import java.util.List;

public class CardRunBean implements Serializable {

    private int code;
    private String msg;
    private CardRun cardRun;

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

    public CardRun getCardRun() {
        return cardRun;
    }

    public void setCardRun(CardRun cardRun) {
        this.cardRun = cardRun;
    }

    public static class CardRun implements Serializable{

        private int accumulated_mileage;//累计里程（米）

        private int max_speed;//最高时速（km/h）
        private int avg_speed;//平均时速（km/h）
        private int cycling_count;//骑行次数
        private List<Mileage_statics> mileage_statics;

        public List<Mileage_statics> getMileage_statics() {
            return mileage_statics;
        }

        public void setMileage_statics(List<Mileage_statics> mileage_statics) {
            this.mileage_statics = mileage_statics;
        }

        public int getAccumulated_mileage() {
            return accumulated_mileage;
        }

        public void setAccumulated_mileage(int accumulated_mileage) {
            this.accumulated_mileage = accumulated_mileage;
        }

        public int getMax_speed() {
            return max_speed;
        }

        public void setMax_speed(int max_speed) {
            this.max_speed = max_speed;
        }

        public int getAvg_speed() {
            return avg_speed;
        }

        public void setAvg_speed(int avg_speed) {
            this.avg_speed = avg_speed;
        }

        public int getCycling_count() {
            return cycling_count;
        }

        public void setCycling_count(int cycling_count) {
            this.cycling_count = cycling_count;
        }

        public static class Mileage_statics {

            private String day;//时间天

            private int mileage;//里程（米）


            public void setDay(String day) {
                this.day = day;
            }

            public String getDay() {
                return day;
            }

            public void setMileage(int mileage) {
                this.mileage = mileage;
            }

            public int getMileage() {
                return mileage;
            }

        }
    }
}


