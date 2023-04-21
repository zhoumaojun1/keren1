package com.wja.keren.user.home.mine;


import android.annotation.SuppressLint;
import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.bean.CardRunBean;
import com.wja.keren.user.home.bean.CardRunListBean;
import com.wja.keren.user.home.bean.LoginInfoBean;
import com.wja.keren.user.home.network.HtlRetrofit;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardRunPresenter extends BasePresenterImpl<CardRunSet.View> implements CardRunSet.Presenter{

    public CardRunPresenter(Context context) {
        super(context);
    }


    /**
     *车辆行驶轨迹列表
     * @param index
     * @param size
     * @param start_time
     * @param end_time
     */
    @SuppressLint("CheckResult")
    @Override
    public void runRouteList(Integer id,Integer index, Integer size, Integer start_time, Integer end_time) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ebike_id",id);
        hashMap.put("size",size);
        hashMap.put("start_time",start_time);
        hashMap.put("end_time",end_time);
        HtlRetrofit.getInstance().getService(2).cardRouteList(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardList -> {
                    if (cardList !=null){
                        CardRunListBean cardRunListBean = JSONObject.parseObject(String.valueOf(cardList), CardRunListBean.class);
                        if ("ok".equals(cardRunListBean.getMsg())||"200".equals(cardRunListBean.getCode())){
                            view.showCardList(cardRunListBean.getData().getList());
                            view.showMessage("轨迹列表成功");
                        }else {
                            view.showMessage("轨迹列表失败");
                        }

                    }


                });
    }

    /**
     * 骑行数据统计
     * @param time_start
     * @param end_time
     * @param ebike_id
     */
    @SuppressLint("CheckResult")
    @Override
    public void runRouteAllData(Integer time_start, Integer end_time, Integer ebike_id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ebike_id",ebike_id);
        hashMap.put("time_start",time_start);
        hashMap.put("end_time",end_time);

        HtlRetrofit.getInstance().getService(2).cardRunAllData(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result == null) {
                        return;
                    }
                    CardRunBean cardRunBean = JSONObject.parseObject(String.valueOf(result), CardRunBean.class);
                    if (cardRunBean.getCode() == 200 || cardRunBean.getMsg().equals("ok")) {
                        view.showMessage("骑行统计成功");
                        view.showRouteAllData(cardRunBean);
                    }

                });
    }
}
