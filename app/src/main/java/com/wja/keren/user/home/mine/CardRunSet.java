package com.wja.keren.user.home.mine;

import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;
import com.wja.keren.user.home.bean.CardRunBean;
import com.wja.keren.user.home.bean.CardRunListBean;

import java.util.List;

public class CardRunSet {

    public interface Presenter extends BasePresenter {


        void runRouteList(Integer id,Integer index,Integer size,Integer start_time,Integer end_time);
        void runRouteAllData(Integer time_start,Integer end_time,Integer ebike_id);

    }

     public interface View extends BaseView {



        void showCardList( List<CardRunListBean.AllList.OneList> userHead);

        void showRouteAllData(CardRunBean cardRunBean);

    }

}
