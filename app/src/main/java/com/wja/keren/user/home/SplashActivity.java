package com.wja.keren.user.home;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.zxing.util.IntentUtil;


public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    IntentUtil.get().goActivity(SplashActivity.this, TwoSplashActivity.class);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }
}