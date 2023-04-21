package com.wja.keren.user.home.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;


import com.alibaba.fastjson.JSONObject;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.bean.UserInfoBean;
import com.wja.keren.user.home.main.SelectRoleActivity;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.network.HtlUserRetrofit;
import com.wja.keren.zxing.util.IntentUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MinePresenter extends BasePresenterImpl<MineContact.View> implements MineContact.Presenter {


    private final static String TAG = MinePresenter.class.getSimpleName();

    public MinePresenter(Context context) {
        super(context);
    }


    @Override
    public void onLogout() {

    }


    @SuppressLint("CheckResult")
    @Override
    public void getUserInfo() {
        HtlUserRetrofit.getInstance().getService(1).getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    UserInfoBean userInfoBean = JSONObject.parseObject(String.valueOf(userInfo), UserInfoBean.class);
                    if (null != userInfoBean && userInfoBean.getCode() == 200
                            || userInfoBean.getMsg().equals("ok") && null != userInfoBean.getData()) {
                        view.showUserName(userInfoBean.getData());
                    }
                });


    }


    @Override
    public void unregister() {

    }

    @Override
    public void uploadAvatar(String path) {


    }

    @Override
    public void modifyNickname(String nickName) {

    }


}
