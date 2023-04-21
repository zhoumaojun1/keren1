package com.wja.keren.user.home.auth;

import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;

class ForgetPasNextContact {

    interface Presenter extends BasePresenter {


        void findPassWord(String code,String account,String password);
        boolean checkPasswordValid(String password,String newPassword,boolean showError);
        void checkPasswordForm(String password,boolean showError);
    }

    interface View extends BaseView {

        /**
         *
         * @param type 0 is email, 1 is phone number
         */
        void showAccountValid(int type);

        /**
         *
         * @param message string resource id
         */

        void showPasswordError(int message);

        void onFinishPasResult(String user);

    }

}
