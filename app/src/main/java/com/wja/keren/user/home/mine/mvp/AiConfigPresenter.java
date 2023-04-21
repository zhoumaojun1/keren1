package com.wja.keren.user.home.mine.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.bean.CardConfigBean;
import com.wja.keren.user.home.network.HtlRetrofit;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AiConfigPresenter extends BasePresenterImpl<AiConfig.View> implements AiConfig.Presenter{
    public AiConfigPresenter(Context context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    @Override
    public void setAiConfig(int flag, boolean isCheck ,int id) {

        HashMap<String, Object> hashMap = new HashMap<>();

        HashMap<String, Object> hashMap1 = new HashMap<>();

        hashMap1.put("id", id);
        if (flag == 1 ){
            hashMap.put("open_lock",isCheck ? 0:1);
        } else if (flag == 2){
            hashMap.put("hitchs_up", isCheck ? 0:1);
        } else if (flag == 3){
            hashMap.put("alarm",isCheck ? 0:1);
        } else if (flag == 4){
            hashMap1.put("fly_car",isCheck ? 0:1);
        }else if (flag == 5){
            hashMap1.put("hill_assist_range", isCheck ? 0:1);
        } else if (flag == 6){
            hashMap1.put("hill_assist_range", isCheck ? 0:1);
        }
        hashMap1.put("ebike_set",hashMap);

        System.out.println("入参数 == " +hashMap1);
        HtlRetrofit.getInstance().getService(2).setAiConfig(
                        hashMap1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAiConfig() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("index", 0);
        hashMap.put("size", 10);
        hashMap.put("ebike_id", Config.DEVICE_ID);
        HtlRetrofit.getInstance().getService(2).getAiConfig(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aiInfo -> {
                    if (aiInfo != null) {
                        CardConfigBean cardConfig = JSONObject.parseObject(String.valueOf(aiInfo), CardConfigBean.class);
                        if (null != cardConfig && "ok".equals(cardConfig.getMsg()) || "200".equals(cardConfig.getCode())) {
                            CardConfigBean.CardConfig.Ebike_set ebike_set = cardConfig.getData().getEbike_set();
                            view.showAiConfigInfo(cardConfig);
                          //  view.showMessage("车辆智能配置获取参数成功");
                        } else {
                            Log.e("aiInfo is error ", cardConfig.getMsg());
                        }

                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void setAiConfig1(int flag, String value, int id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("id", Config.DEVICE_ID);

        if (flag == 1 ){
            hashMap.put("power_value",Integer.parseInt(value));
        } else if (flag == 2){
            hashMap.put("leave_countdown",Integer.parseInt(value));
        } else if (flag == 3){
            hashMap.put("open_lock_distance",value);
        }
        hashMap1.put("ebike_set",hashMap);

        System.out.println("setAiConfig1入参数 == " +hashMap1);
        HtlRetrofit.getInstance().getService(2).setAiConfig(
                        hashMap1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        Log.d("setAiConfig","成功");

                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void openAiConfig(int flag, String ebike_id,boolean isCheck, int value) {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap.put("id", Config.DEVICE_ID);
        if (flag == 1) {
            hashMap.put("prevent_out_of_control", isCheck ? 0 : 1);
        } else if (flag == 2) {
            hashMap.put("hill_assist", isCheck ? 1 : 0);
        } else if (flag == 3) {
            hashMap.put("senseless_unlock",0);
            if ("强".equals(ebike_id)){
                hashMap.put("senseless_unlock",3);

            }else if ("中".equals(ebike_id)){
                hashMap1.put("senseless_unlock",2);

            }else {
                hashMap.put("senseless_unlock",1);

            }

        }


        System.out.println("openAiConfig入参数 == " +hashMap1);
        HtlRetrofit.getInstance().getService(2).openAiConfig(
                        hashMap1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        Log.d("setAiConfig","成功");

                    }
                });
    }
}
