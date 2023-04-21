package com.wja.keren.user.home.main;


import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;
import com.wja.keren.user.home.bean.CardInfoBean;
import com.wja.keren.user.home.bean.CardShareBean;
import com.wja.keren.user.home.bean.UserShareCardListBean;

public class MainContact  {


    public interface Presenter extends BasePresenter {
        void CardScanCodeBind(String cardSn);
        void CardShareAgree(int deviceId);

        void CardShareList(int deviceId,int type);
        void CardBleConnectBind();

        void CancelCardShare(int id);
    }



    public  interface View extends BaseView {
         void showCardInfo( CardInfoBean.CardDetailed cardDetailed);

        void showCardShareList(CardShareBean.CardShare  userHead);

        void updateShareNumberList();
        void showCardShareUserNumber(UserShareCardListBean.UserShareCard cardRunBean);
    }

}
