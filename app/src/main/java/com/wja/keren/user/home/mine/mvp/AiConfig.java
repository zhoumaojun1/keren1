package com.wja.keren.user.home.mine.mvp;


import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;
import com.wja.keren.user.home.bean.CardConfigBean;

/**
 * 车辆位置
 */

public class AiConfig {


      public interface Presenter extends BasePresenter {
        void setAiConfig(int flag, boolean isCheck,int id);
        void getAiConfig();

          void setAiConfig1(int flag,String value,int id);

          void  openAiConfig(int flag,String ebike_id,boolean isCheck,int value);
    }



    public interface View extends BaseView {
         void updateAiConfig( );
        void showAiConfigInfo( CardConfigBean cardConfig);
    }

}
