package com.wja.keren.user.home.auth;

import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;

class LoginContact {

    interface Presenter extends BasePresenter {


        void passWordLogin(String account,String password);
        void checkAccountValid(String account,boolean showError);
        void checkPasswordValid(String password,boolean showError);
        void verifyCodeLogin(String phone,String code);
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
        void showAccountError(int message);
        void showPasswordError(String message);
        void showPasswordError(int message);
        void onLoginResult(Object user);
        void onVerifyLoginResult(Object user);
    }

}
