package com.wja.keren.user.home.mine.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.user.home.base.BasePresenterImpl;

import com.wja.keren.user.home.bean.CardLocationBean;
import com.wja.keren.user.home.network.HtlRetrofit;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardLocationPresenter extends BasePresenterImpl<CardLocation.View> implements CardLocation.Presenter {
    public CardLocationPresenter(Context context) {
        super(context);
    }


    @SuppressLint("CheckResult")
    @Override
    public void queryLocation(int ebike_id, String cmd) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ebike_id", ebike_id);
        hashMap.put("cmd", cmd);
        HtlRetrofit.getInstance().getService(2).queryBattery(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {
                    if (location != null) {
                        CardLocationBean locationBean = JSONObject.parseObject(String.valueOf(location), CardLocationBean.class);
                        if (null != locationBean && locationBean.getCode() == 200 || "ok".equals(locationBean.getMsg())) {
                            view.showMessage("查询车辆位置成功");
                            view.showCardLocationInfo(locationBean.getCardLocation());
                        } else {
                            Log.e("card location is error =", locationBean.getCode() + "");
                            view.showMessage(locationBean.getMsg());
                        }
                    } else {
                        Log.e("card location is null ", "");
                    }
                });
    }
}
