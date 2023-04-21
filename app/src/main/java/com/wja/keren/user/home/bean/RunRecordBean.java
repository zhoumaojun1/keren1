package com.wja.keren.user.home.bean;

import java.io.Serializable;

public class RunRecordBean implements Serializable {
    private String runTime;
    private String cardPhoto;
    private String runStartLocation;
    private String runEndLocation;
    private String runMileage;
    private String spendTime;
    private String runSpeed;

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getCardPhoto() {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    public String getRunStartLocation() {
        return runStartLocation;
    }

    public void setRunStartLocation(String runStartLocation) {
        this.runStartLocation = runStartLocation;
    }

    public String getRunEndLocation() {
        return runEndLocation;
    }

    public void setRunEndLocation(String runEndLocation) {
        this.runEndLocation = runEndLocation;
    }

    public String getRunMileage() {
        return runMileage;
    }

    public void setRunMileage(String runMileage) {
        this.runMileage = runMileage;
    }

    public String getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(String spendTime) {
        this.spendTime = spendTime;
    }

    public String getRunSpeed() {
        return runSpeed;
    }

    public void setRunSpeed(String runSpeed) {
        this.runSpeed = runSpeed;
    }
}
