package com.wja.keren.user.home.bean;

import java.io.Serializable;

public class BleScanResultBean implements Serializable {
    private String uuId;
    private String deviceName;
    private int deviceId;
    private int cardCode;
    private String cardPhoto;
    private String pairStatus;

    public String getPairStatus() {
        return pairStatus;
    }

    public void setPairStatus(String pairStatus) {
        this.pairStatus = pairStatus;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getCardCode() {
        return cardCode;
    }

    public void setCardCode(int cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardPhoto() {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }
}
