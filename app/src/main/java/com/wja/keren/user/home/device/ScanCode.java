package com.wja.keren.user.home.device;


import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;
import com.wja.keren.user.home.bean.ScanCodeBindBean;

public class ScanCode {
    public interface Presenter extends BasePresenter {


        public void snQueryDeviceInfo(String sn_code);

        public void nowBindDevice(String sn_code);

    }

    public interface View extends BaseView {

        void showQueryDeviceList(ScanCodeBindBean.ScanCodeBind scanCodeBind);


        void updateBindDevice();



    }

}
