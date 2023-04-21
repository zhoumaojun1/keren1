package com.wja.keren.user.home.mine.mvp;


import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;
import com.wja.keren.user.home.bean.CardInfoBean;
import com.wja.keren.user.home.bean.CardLocationBean;
import com.wja.keren.user.home.bean.CardShareBean;
import com.wja.keren.user.home.bean.UserShareCardListBean;

/**
 * 车辆位置
 */

public class CardLocation {


      public interface Presenter extends BasePresenter {
        void queryLocation(int ebike_id,String cmd);

    }



    public interface View extends BaseView {
         void showCardLocationInfo( CardLocationBean.CardLocation location);

    }

}
