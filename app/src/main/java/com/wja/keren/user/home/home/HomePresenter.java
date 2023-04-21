package com.wja.keren.user.home.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.bean.BatteryBean;
import com.wja.keren.user.home.bean.CardBindBean;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.network.HtlRetrofit;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class HomePresenter extends BasePresenterImpl<HomeContact.View> implements HomeContact.Presenter {

    private static final String TAG = HomePresenter.class.getSimpleName();



    public HomePresenter(Context context) {
        super(context);
    }



    @SuppressLint("CheckResult")
    @Override
    public void openAndCloseCard(int device_id, int on_off) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("device_id",device_id);
        hashMap.put("on_off",on_off);
        HtlRetrofit.getInstance().getService(2).cardOpen(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardOnAndOff -> {
                    if (cardOnAndOff != null && on_off == 1) {
                        view.showMessage("车子开启成功");
                        view.updateOpenBg(1);
                    } else {
                        view.updateOpenBg(2);
                        view.showMessage("车子关闭成功");
                    }
                });
    }
    @SuppressLint("CheckResult")
    @Override
    public void queryBattery(int ebike_id, String cmd) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ebike_id",ebike_id);
        hashMap.put("cmd",cmd);
        HtlRetrofit.getInstance().getService(2).queryBattery(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(batteryInfo -> {
                    Log.d(TAG,"queryBattery 电池剩余 = " +batteryInfo);
                    if (batteryInfo != null) {
                        BatteryBean batteryBean = JSONObject.parseObject(String.valueOf(batteryInfo), BatteryBean.class);
                        if (null != batteryBean && batteryBean.getCode() == 200 || "ok".equals(batteryBean.getMsg())) {
                            view.showMessage("查询电池剩余量成功");
                            view.showBattery(batteryBean.getBattery());
                        } else {
                            Log.e("battery is error =", batteryBean.getCode() + "");
                            view.showMessage(batteryBean.getMsg());
                        }
                    } else {
                        Log.e("battery is null ","");
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void CardScanCodeBind(String cardSn) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sn_code",cardSn);
        HtlRetrofit.getInstance().getService(2).cardBind(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    Log.d(TAG,"绑定车辆" +cardInfo);
                    if (cardInfo != null) {
                        CardBindBean cardBindBean = JSONObject.parseObject(String.valueOf(cardInfo), CardBindBean.class);
                        view.showMessage("扫码绑定成功");
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCardList() {
        HtlRetrofit.getInstance().getService(2).cardList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        CardListBean cardList = JSONObject.parseObject(String.valueOf(cardInfo), CardListBean.class);
                        if (cardList != null && cardList.getData() != null && cardList.getData().size() > 0) {
                            ArrayList<CardListBean.CardList> myCardList = new ArrayList<>();
                            for (CardListBean.CardList cardListBean : cardList.getData()) {
                                myCardList.add(cardListBean);
                                Log.d("绑定 ==" ,myCardList.size()+"");
                                view.showMessage("获取车辆列表成功====="+myCardList.size());
                                view.showDevice(myCardList);
                            }
                        } else {
                            view.showDevice(cardList.getData());
                        }
                    }
                });
    }
    @SuppressLint("CheckResult")
    @Override
    public void cushionRebound() {
        HtlRetrofit.getInstance().getService(2).cardList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        CardListBean cardList = JSONObject.parseObject(String.valueOf(cardInfo), CardListBean.class);
                        if (cardList != null && cardList.getData() != null && cardList.getData().size() > 0) {
                            ArrayList<CardListBean.CardList> myCardList = new ArrayList<>();
                            for (CardListBean.CardList cardListBean : cardList.getData()) {
                                myCardList.add(cardListBean);
                                //   view.showMessage("获取车辆列表成功");
                                view.showDevice(myCardList);
                            }
                        } else {
                            view.showDevice(cardList.getData());
                        }
                    }
                });
    }
    @SuppressLint("CheckResult")
    @Override
    public void hornFindCard(int ebike_id, int cmd) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ebike_id",ebike_id);
        hashMap.put("cmd",cmd);
        HtlRetrofit.getInstance().getService(2).sendCardDownCmd(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        view.showMessage("坐垫弹出指令下发成功");
                    }
                });
    }
    @SuppressLint("CheckResult")
    @Override
    public void playCardSound(int ebike_id, int cmd) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ebike_id",ebike_id);
        hashMap.put("play_voice",cmd);
        HtlRetrofit.getInstance().getService(2).sendCardMessage(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(video -> {
                    if (video != null) {
                        if (cmd == 0) {
                            view.showMessage("喇叭开启寻车接口成功");
                        } else if (cmd == 1) {
                            view.showMessage("喇叭关闭寻车接口成功");
                        } else if (cmd == 2) {
                            view.showMessage("喇叭寻车接口成功");
                        } else if (cmd == 8) {
                            view.showMessage("喇叭寻车接口成功");
                        }

                    } else {
                        Log.e("card location is null ","");
                    }
                });
    }


}
