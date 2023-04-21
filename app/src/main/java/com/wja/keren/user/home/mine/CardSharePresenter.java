package com.wja.keren.user.home.mine;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.bean.AgreeShareCardBean;
import com.wja.keren.user.home.bean.BaseBean;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.bean.CardShareBean;
import com.wja.keren.user.home.bean.UserShareCardListBean;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.view.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardSharePresenter extends BasePresenterImpl<CardShareCom.View> implements CardShareCom.Presenter{

    public CardSharePresenter(Context context) {
        super(context);
    }

//    @SuppressLint("CheckResult")
//    @Override
//    public void cardShare(int device_id, int type) {
//        HashMap<String, Object> hashMap = new HashMap<>();
////        hashMap.put("device_id", device_id);
////        hashMap.put("type", type);
//        HtlRetrofit.getInstance().getService(2).useShareCardList(
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(result -> {
//                    if (result!=null){
//                        UserShareCardListBean cardShareBean=JSONObject.parseObject(String.valueOf(result),UserShareCardListBean.class);
//                        if (cardShareBean != null && "ok".equals(cardShareBean.getMsg()) || 200 == cardShareBean.getCode()) {
//                            if (null != cardShareBean.getData()) {
//                                Log.d("cardShare", cardShareBean.getData() + "---");
//                                view.showCardShare(cardShareBean.getData());
//                            }
//                        } else {
//                            view.showMessage(cardShareBean.getMsg());
//                        }
//                    }
//
//                });
//    }

    @SuppressLint("CheckResult")
    @Override
    public void cardShare(int device_id, int type) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("device_id", device_id);
        hashMap.put("type", type);
        HtlRetrofit.getInstance().getService(2).useShareCardList(
                         )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result!=null){
                        CardShareBean cardShareBean=JSONObject.parseObject(String.valueOf(result),CardShareBean.class);
                        if (cardShareBean != null && "ok".equals(cardShareBean.getMsg()) || 200 == cardShareBean.getCode()) {
                            if (null != cardShareBean.getData()) {
                                Log.d("cardShare", cardShareBean.getData() + "---");
                                view.showCardShare(cardShareBean.getData());
                            }
                        } else {
                            view.showMessage(cardShareBean.getMsg());
                        }
                    }

                });
    }
    @SuppressLint("CheckResult")
    @Override
    public void CardShareAgree(int device_id, int type) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("device_id",device_id);
        hashMap.put("type",1);
        HtlRetrofit.getInstance().getService(2).CardShareAgree(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result == null) {
                        return;
                    }
                    CardShareBean cardRunBean = JSONObject.parseObject(String.valueOf(result), CardShareBean.class);
                    if (cardRunBean.getCode() == 0 && cardRunBean.getMsg().equals("ok")) {
                        view.showMessage("用车分享/车辆分享列表");
                        view.showShareAgree(cardRunBean.getData());
                    }
                });
    }
    @SuppressLint("CheckResult")
    @Override
    public void cardGarage() {
        HtlRetrofit.getInstance().getService(2).cardList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result != null) {
                        CardListBean cardList = JSONObject.parseObject(String.valueOf(result), CardListBean.class);
                        if (cardList != null && cardList.getData() != null && cardList.getData().size() > 0) {
                            ArrayList<CardListBean.CardList> myCardList = new ArrayList<>();
                            for (CardListBean.CardList cardListBean : cardList.getData()) {
                                myCardList.add(cardListBean);
                                view.showGarage(myCardList);
                                //   view.showMessage("获取车辆列表成功");

                            }
                        } else {
                            view.showMessage(cardList.getMsg());
                        }
                    }


                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void acceptCardShare(int shareId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",shareId);
        HtlRetrofit.getInstance().getService(2).acceptCardShare(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result == null) {
                        return;
                    }
                    AgreeShareCardBean agreeShareCardBean = JSONObject.parseObject(String.valueOf(result), AgreeShareCardBean.class);
                    if (null == agreeShareCardBean) {
                    } else {
                        if (agreeShareCardBean.getMessage().equals("ok") || agreeShareCardBean.getCode() == 200) {
                            view.showMessage("接受分享成功");
                            //view.showGarage(cardRunBean.getData());
                        }

                    }

                });
    }
}
