package com.wja.keren.user;

public class Constant {
    public static final String EMAIL_PATTERN = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
    public static final String PHONE_NUMBER_PATTERN = "\\d{7,}$";
    public static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]*.{6,50}";
    public static final String FULL_NAME_PATTERN = "^[\\w&' \\|\\-\\_]{2,50}";

    /**
     * 日志打印控制
     */
    // 阿里云日志
    public static final  boolean LOG_ALIYUN = true;
    // 属性的日志
    public static final boolean LOG_PROPERTY = true;
    // AliyunService


}
