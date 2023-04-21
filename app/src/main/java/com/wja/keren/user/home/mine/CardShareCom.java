package com.wja.keren.user.home.mine;

import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.bean.CardShareBean;
import com.wja.keren.user.home.bean.UserShareCardListBean;

import java.util.List;

public class CardShareCom {

    public interface Presenter extends BasePresenter {


        void cardShare(int device_id,int type);
        void CardShareAgree(int device_id,int type);
        void cardGarage();


        void acceptCardShare(int shareId);
    }

     public interface View extends BaseView {



        void showCardShare(CardShareBean.CardShare  userHead);

        void showShareAgree(CardShareBean.CardShare cardRunBean);
         void showGarage(List<CardListBean.CardList> cardRunBean);
    }

}
