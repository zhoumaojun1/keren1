package com.wja.keren.user.home;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class Config {
    private static final String TAG = "Config";
    @APP_Config
    public static final int I_REMAIN_THRESHOLD = 10;




    @APP_Config
    public static final long L_SYNC_MIN_INTERVAL_MS = 2 * 60 * 1000L;    //1 * 60 * 60 * 1000L;



    /**
     * 秘钥
     */
    public static final String API_SECKEY = "1bdfd56ad9ce487ffd0dccc22fed1fd9";
    public static final String REALM_SECKEY = "917FA475D23A1A41CAFFA313C9FB69EE5F936DF3F46CC79CEFB494FF6F03465D";

    public static  String USER_TOKEN = "";
    public static  int DEVICE_ID = 0;

    public static  int USER_ID = 0;
    public static  int DEVICE_SIZE = 10;
    public static  String DEVICE_NAME = "";
    @Documented
    @Target({ElementType.FIELD})
    public @interface APP_Config {

    }


}
