package com.wja.keren.user.home.main;


import android.annotation.SuppressLint;
import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.bean.BaseBean;
import com.wja.keren.user.home.bean.CardInfoBean;
import com.wja.keren.user.home.bean.CardShareBean;
import com.wja.keren.user.home.network.HtlRetrofit;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenterImpl<MainContact.View> implements MainContact.Presenter {


    public MainPresenter(Context context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    @Override
    public void CardScanCodeBind(String cardSn) {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("cardSn",cardSn);
//        HtlRetrofit.getInstance().getService(2).cardBind(hashMap)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(userInfo -> {
//                    view.showMessage("扫码绑定成功");
//                    });
    }

    @SuppressLint("CheckResult")
    @Override
    public void CardBleConnectBind() {
//        HtlRetrofit.getInstance().getService(2).getVerificationCode("phone")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(userInfo -> {
//                  view.showMessage("车子蓝牙链接成功");
//                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void CancelCardShare(int id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        HtlRetrofit.getInstance().getService(2).cancelCardShare(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        BaseBean baseBean = JSONObject.parseObject(String.valueOf(cardInfo), BaseBean.class);
                        if (null != baseBean && "ok".equals(baseBean.getMessage()) || 200 == baseBean.getCode()) {
                            view.showMessage("取消分享成功");
                        }
                    }
                });
    }

    /**
     * 查询车辆信息
     */
    @SuppressLint("CheckResult")
    @Override
    public void CardShareAgree(int deviceId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", deviceId);
        HtlRetrofit.getInstance().getService(2).cardInfo(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        CardInfoBean.CardDetailed cardDetailed = JSONObject.parseObject(String.valueOf(cardInfo), CardInfoBean.CardDetailed.class);
                        if (null!=cardDetailed){
                            view.showCardInfo(cardDetailed);
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void CardShareList(int deviceId, int type) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("device_id", deviceId);
        hashMap.put("type", type);
        HtlRetrofit.getInstance().getService(2).CardShareList(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result != null) {
                        CardShareBean cardShareBean = JSONObject.parseObject(String.valueOf(result), CardShareBean.class);
                        if (cardShareBean != null && "ok".equals(cardShareBean.getMsg()) || 200 == cardShareBean.getCode()) {
                            if (null != cardShareBean.getData()) {
                                view.showCardShareList(cardShareBean.getData());
                            }
                        } else {

                            view.showMessage(cardShareBean.getMsg());
                        }
                    }
                });

//        HtlRetrofit.getInstance().getService(2).useShareCardList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(cardInfo -> {
//                    if (cardInfo != null) {
//                        UserShareCardListBean.UserShareCard userShareCard = JSONObject.parseObject(String.valueOf(cardInfo), UserShareCardListBean.UserShareCard.class);
//                        view.showCardShareUserNumber(userShareCard);
//                        view.showCardShareList(userShareCard.getList());
//                    }
//                });
    }
}
