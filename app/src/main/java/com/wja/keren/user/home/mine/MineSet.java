package com.wja.keren.user.home.mine;

import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;
import com.wja.keren.user.home.bean.UserSetCardBean;

class MineSet {

    interface Presenter extends BasePresenter {


        void editUserHead(String name,String picture);
        void editUserNick(String userId,String nick);
        void checkPasswordForm(String password,boolean showError);
        void  setUseAiConfig(int flag, boolean isCheck);
        void getUserConfig();
    }

    interface View extends BaseView {



        void showUserHead(String userHead);

        void showUserNick(String userNick);
        void showConfigInfo(UserSetCardBean cardConfigBean);
    }

}
