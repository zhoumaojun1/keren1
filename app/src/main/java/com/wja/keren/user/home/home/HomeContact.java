package com.wja.keren.user.home.home;


import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;
import com.wja.keren.user.home.bean.BatteryBean;
import com.wja.keren.user.home.bean.CardListBean;

import java.util.List;

public class HomeContact {
    public interface Presenter extends BasePresenter {


         void openAndCloseCard(int device_id,int on_off);

          void queryBattery(int ebike_id,String cmd);
        void CardScanCodeBind(String cardSn);

        void getCardList();

        void cushionRebound();

        void hornFindCard(int ebike_id,int cmd);

        void playCardSound(int ebike_id,int cmd);
    }

    public interface View extends BaseView {

        void showDevice(List <CardListBean.CardList> list);


        void updateOpenBg(int success);

        void showBattery(BatteryBean.Battery battery);


        void updateCushionRebound();

        void updateHornFindCard();

        void updatePlayCardSound();

    }

}
