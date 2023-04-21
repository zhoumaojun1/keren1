package com.wja.keren.user.home.auth;

import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;

class ForgetPasContact {

    interface Presenter extends BasePresenter {


        void FindPassWord(String account,String password);

        boolean verificationCodeSuccess(String phone,String code);
        boolean verificationCode(String password,boolean showError);

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

        void onFinishPasResult(String verifyCode);


    }

}
