package com.wja.keren;

import static com.amap.api.location.AMapLocationClient.setApiKey;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;


import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.ServiceSettings;
import com.amap.api.services.poisearch.PoiSearch;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by wuwang on 2017/10/30.
 */

public class DemoApplication extends Application {

    private static final String TAG = "DemoApplication";

    //private RefWatcher mRefWatcher ;

    private static DemoApplication application;
    private static Context mCtx;
    public static Context getApplication() {
        return mCtx;
    }
    public static DemoApplication getApplication1() {
        return application;
    }
//    public static RefWatcher getRefWatcher(Context mContext) {
//        return    DemoApplication.getApplication().mRefWatcher;
//
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mCtx = getApplicationContext();
        // mRefWatcher = LeakCanary.install(application);
        ServiceSettings.updatePrivacyShow(mCtx,true,true);
        ServiceSettings.updatePrivacyAgree(mCtx,true);
        setApiKey("a9f7013a220547c7fa23982bfaa54ded");
        sHA1(mCtx);
//        try {
//            PoiSearch mPoiSearch = new PoiSearch(mCtx);
//        } catch (AMapException e) {
//        }
    }
    public static String sHA1(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            Log.d("DemoApplication SHA=  ", result);
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void attachBaseContext(Context base) {
       // MultiLanguageUtil.getInstance().saveSystemCurrentLanguage(base);
        super.attachBaseContext(base);
        //app刚启动getApplicationContext()为空
      //  MultiLanguageUtil.getInstance().setConfiguration(getApplicationContext());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //app刚启动不一定调用onConfigurationChanged
    //    MultiLanguageUtil.getInstance().setConfiguration(getApplicationContext());
    }


}
