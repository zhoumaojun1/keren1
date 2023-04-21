package com.wja.keren.user.home.mine;


import static com.wja.keren.user.home.Config.USER_ID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.bean.UserSetCardBean;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.network.HtlUserRetrofit;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MineSetPresenter  extends BasePresenterImpl<MineSet.View> implements MineSet.Presenter{

    public MineSetPresenter(Context context) {
        super(context);
    }


    @SuppressLint("CheckResult")
    @Override
    public void editUserHead(String name, String picture) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("picture", picture);
        System.out.println("editUserHead ==" +hashMap);
        HtlUserRetrofit.getInstance().getService(1).changeUserInfo(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    view.showUserHead("");
                });

    }

    @Override
    public void editUserNick(String userId, String nick) {

    }

    @Override
    public void checkPasswordForm(String password, boolean showError) {

    }
    @SuppressLint("CheckResult")
    @Override
    public void setUseAiConfig(int flag, boolean isCheck) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", Config.DEVICE_ID);
        if (flag == 1) {
            hashMap.put("vibration", isCheck ? 1 : 0);
        } else if (flag == 2) {
            hashMap.put("cut_power", isCheck ? 1 : 0);
        } else if (flag == 3) {
            hashMap.put("charge_monitor", isCheck ? 1 : 0);
        } else if (flag == 4) {
            hashMap.put("guard_alarm", isCheck ? 1 : 0);
        } else if (flag == 5) {
            hashMap.put("fault_alarm", isCheck ? 1 : 0);
        } else if (flag == 6) {
            hashMap.put("fingerprint", isCheck ? 1 : 0);
        }else if (flag == 7) {
            hashMap.put("face", isCheck ? 1 : 0);
        }
        HtlRetrofit.getInstance().getService(2).setUseAiConfig(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {

                        Log.d("TAG","setUseAiConfig==" +USER_ID+"----"+flag);

                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getUserConfig() {
        HashMap<String,Object>hashMap =new HashMap<>();
        HtlRetrofit.getInstance().getService(2).getUseAiConfig(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        UserSetCardBean  cardConfigBean = JSONObject.parseObject(String.valueOf(cardInfo), UserSetCardBean.class);
                        if (null != cardConfigBean && cardConfigBean.getMsg().equals("ok") || cardConfigBean.getCode() == 200) {
                            view.showConfigInfo(cardConfigBean);

                        }
                    }
                });
    }
}
