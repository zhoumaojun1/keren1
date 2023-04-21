package com.wja.keren.user.home.find;


import com.wja.keren.user.home.base.BasePresenter;
import com.wja.keren.user.home.base.BaseView;

class FindDevice {

     interface Presenter extends BasePresenter {

          /**
           *
           * @param unread 0 未读消息, 1 表示已读
           */
           void getMessage(boolean unread);

          /**
           *
           * @param unread 消息状态
           * @param year  年
           * @param month 月 1月从0开始
           * @param day 日
           */
          void getMessage(boolean unread,int year,int month,int day);

          /**
           * 复位消息参数
           */
          void resetParam();

     }
     interface View extends BaseView {
          void onUpdateMessage(String list);
          void refreshMessage();
     }

     interface SetPresenter extends  BasePresenter {

          void getGlobalMessage();
          /**
           *  全局消息设置
           * @param enable true 开,false 关
           */
          void setGlobalMessage(boolean enable);
          /**
           *  设置事件告警开关,影响所有设备
           * @param enable 开关
           */
          void setEventNotify(boolean enable);

          /**
           *  消息推送设置
           * @param enable true 开,false 关
           */
          void setNotify(boolean enable);

          /**
           * 消息推送时间
           * @param start 开始时间
           * @param end 结束时间
           * TODO 消息推送没有时段设置,只有时区
           */
          void setNotifyTime(int start,int end);
     }



}
