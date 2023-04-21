package com.wja.keren.user.home.mine;


import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;
import com.wja.keren.user.home.bean.UserInfoBean;

class MineContact {
     interface Presenter extends BasePresenter {

          void onLogout();
          void getUserInfo();
          void unregister();
          void uploadAvatar(String path);
          void modifyNickname(String nickName);
     }

     interface View extends BaseView {
          void showLogout(boolean success);
          void showUserName( UserInfoBean.User userInfoBean);
     }
}
