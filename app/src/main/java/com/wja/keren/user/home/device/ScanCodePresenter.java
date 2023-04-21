package com.wja.keren.user.home.device;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.bean.CardBindBean;
import com.wja.keren.user.home.bean.CardInfoBean;
import com.wja.keren.user.home.bean.ScanCodeBindBean;
import com.wja.keren.user.home.network.HtlRetrofit;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ScanCodePresenter extends BasePresenterImpl<ScanCode.View> implements ScanCode.Presenter {


    public ScanCodePresenter(Context context) {
        super(context);
    }




    /**
     * sn查询设备信息
     */
    @SuppressLint("CheckResult")
    @Override
    public void snQueryDeviceInfo(String sn_code) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sn_code", sn_code);
        HtlRetrofit.getInstance().getService(2).snQueryDeviceInfo(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        ScanCodeBindBean cardScan = JSONObject.parseObject(String.valueOf(cardInfo), ScanCodeBindBean.class);
                        view.showMessage("扫码结果成功");
                        if (cardScan != null && cardScan.getMsg().equals("ok") || "200".equals(cardScan.getCode())) {
                            view.showQueryDeviceList(cardScan.getData());
                        }

                    }
                });
    }

    /**
     * 查询车辆信息
     */
    @SuppressLint("CheckResult")
    @Override
    public void nowBindDevice(String sn_code) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sn_code",sn_code);
        HtlRetrofit.getInstance().getService(2).cardBind(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        //CardBindBean cardBindBean = JSONObject.parseObject(String.valueOf(cardInfo), CardBindBean.class);
                        view.updateBindDevice();
                    }
                });
    }
}
