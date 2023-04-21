package com.wja.keren.user.home.network;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.user.home.bean.CardUnBindBean;
import com.wja.keren.user.home.bean.FindPasswordBean;
import com.wja.keren.user.home.bean.GetVerifyCodeBean;
import com.wja.keren.user.home.bean.LoginInfoBean;
import com.wja.keren.user.home.bean.UserInfoBean;
import com.wja.keren.user.home.bean.VerifyCodeBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HtlService {


    @POST("v1/app/login")
    Observable<JSONObject> loginUser(@Body Map<String ,Object> userInfo);



    /**
     * 用户注册
     * @param userRegisInfo
     * @return
     */

    @POST("Consumables/verify")
    Observable<LoginInfoBean> registerUser(@Body Map<String ,Object> userRegisInfo);

    /**
     * 修改密码
     * @param userPassInfo
     * @return
     */

    @POST("v1/app/user/update_password")
    Observable<FindPasswordBean> editPassword(@Body Map<String ,Object> userPassInfo);



    /**
     * 更换手机号
     * @param userPassInfo
     * @return
     */
    @POST("v1/app/user/replace_phone")
    Observable<JSONObject> changePhonePassword(@Body Map<String ,Object> userPassInfo);



    /**
     * 获取验证码
     * @param userPassInfo
     * @return
     */

    @POST("v1/sms/send")
    Observable<JSONObject> getVerificationCode(@Body Map<String ,Object> userPassInfo);


    /**
     * 校验验证码
     * @param userPassInfo
     * @return
     */

    @POST("v1/sms/check")
    Observable<JSONObject> VerificationCode(@Body Map<String ,Object> userPassInfo);


    /**
     * 找回密码
     * @param
     * @return
     */

    @POST("v1/app/password/update")
    Observable<FindPasswordBean> findPassword();

    /**
     * 用户信息
     * @param
     * @return
     */

    @GET("v1/app/user/info")
    Observable<JSONObject> getUserInfo();


    /**
     * 修改用户信息
     * @param
     * @return
     */

    @POST("v1/app/user/update")
    Observable<JSONObject> changeUserInfo(@Body Map<String ,Object> cardInfo);




    /**
     * 车辆绑定
     * @param cardInfo
     * sn 号
     * @return
     */
    @POST("v1/app/device/bind")
    Observable<JSONObject> cardBind(@Body Map<String ,Object> cardInfo);

    /**
     * sn查询设备信息
     * @param cardInfo
     * sn 号
     * @return
     */
    @POST("v1/app/device/find_by_sn")
    Observable<JSONObject> snQueryDeviceInfo(@Body Map<String ,Object> cardInfo);



    /**
     * 车辆列表
     * sn 号
     * @return
     */

    @POST("v1/app/device/list")
    Observable<JSONObject> cardList();

    /**
     * 车辆详情
     * @param cardInfo
     * sn 号
     * @return
     */
    @POST("v1/app/device/info")
    Observable<JSONObject> cardInfo(@Body Map<String ,Object> cardInfo);


    /**
     * 修改车辆系统设置
     * @param cardInfo
     * sn 号
     * @return
     */
    @POST("v1/app/setting/update")
    Observable<JSONObject> setAiConfig(@Body Map<String ,Object> cardInfo);



    /**
     * 修改车辆系统设置
     * @param cardInfo
     * sn 号
     * @return
     */
    @POST("v1/app/signal/toggle")
    Observable<JSONObject> openAiConfig(@Body Map<String ,Object> cardInfo);




    /**
     * 用户设置更新
     * @param cardInfo
     * sn 号
     * @return
     */
    @POST("v1/app/userSetting/update")
    Observable<JSONObject> setUseAiConfig(@Body Map<String ,Object> cardInfo);

    /**
     * 用户设置详情
     * @return
     */
    @POST("v1/app/userSetting/detail")
    Observable<JSONObject> getUseAiConfig(@Body Map<String ,Object> cardInfo);



    /**
     * 获取车辆系统设置
     * @param cardInfo
     * sn 号
     * @return
     */
    @POST("v1/app/setting/get")
    Observable<JSONObject> getAiConfig(@Body Map<String ,Object> cardInfo);

    /**
     * 车辆解绑
     * @param unbind
     * @return
     */

    @POST("v1/app/device/unbind")
    Observable<JSONObject> cardUnBind(@Body Map<String ,Object> unbind);

    @POST("v1/app/device/lock")
    Observable<JSONObject> cardLock(@Body Map<String ,Object> unbind);



    /**
     * 用车分享/搜索手机号进行车辆分享
     * @param map
     * @return
     */
    @POST("v1/app/share_device/create")
    Observable<JSONObject> cancelCardShare(@Body Map<String ,Object> map);



    /**
     * 取消车辆分享
     * @param map
     * @return
     */
    @POST("v1/app/share_device/delete")
    Observable<JSONObject> addCardShareAccount(@Body Map<String ,Object> map);



    /**
     * 用车分享/车辆分享列表
     * @param map
     * @return
     */
    @POST("v1/app/share_device/list")
    Observable<JSONObject> CardShareList(@Body Map<String ,Object> map);


    /**
     * 分享待接受列表
     * @param map
     * @return
     */
    @POST("v1/app/share_device/list")
    Observable<JSONObject> CardShareAgree(@Body Map<String ,Object> map);



    /**
     * 接受分享
     * @param map
     * @return
     */
    @POST("v1/app/share_device/accept")
    Observable<JSONObject> acceptCardShare(@Body Map<String ,Object> map);



    /**
     * 用户分享车辆列表
     * @return
     */
    @POST("v1/app/share_device/user_share")
    Observable<JSONObject> useShareCardList();

  

    /**
     * 取消分享
     * @param map
     * @return
     */
    @POST("v1/app/share_device/update")
    Observable<JSONObject> CancelShare(@Body Map<String ,Object> map);


    /**
     * 车辆开启关闭
     * @param userInfo
     *  0关闭，1开启
     * @return
     */

    @POST("v1/app/device/switch")
    Observable<Response<JSONObject>> cardOpen(@Body Map<String ,Object> userInfo);


    /**
     *查询电量
     * @param battery
     * @return
     */
    @POST("v1/app/signal/get_msg")
    Observable<JSONObject> queryBattery(@Body Map<String ,Object> battery);


    /**
     *发送消息
     * @param msg
     * @return
     */
    @POST("v1/app/signal/send_msg")
    Observable<JSONObject> sendCardMessage(@Body Map<String ,Object> msg);

    /**
     *下发指令
     * @param cmd
     * @return
     */
    @POST("v1/app/signal/send_cmd")
    Observable<JSONObject> sendCardDownCmd(@Body Map<String ,Object> cmd);


    /**
     * 车辆运行信息上报
     * @param battery_cover
     * @param fall
     * @param foot_open
     * @param frame_code
     * @param mileage
     * @param on_off
     * @param point
     * @param pressure
     * @param sn_code
     * @param speed
     * @param static1
     * @param wheel_roll
     * @return
     */

    @POST("v1/app/device/run_report")
    Observable<FindPasswordBean> cardRunInfoReport(@Field("battery_cover") String battery_cover,@Field("fall") String fall,@Field("foot_open") String foot_open,
                                                   @Field("frame_code") String frame_code,@Field("mileage") String mileage,@Field("on_off") String on_off,
                                                   @Field("point") String point,@Field("pressure") String pressure,@Field("sn_code") String sn_code,
                                                   @Field("speed") String speed,@Field("static") String static1,@Field("wheel_roll") String wheel_roll);

    /**
     * 车辆行驶轨迹列表
     * @param route
     * @return
     */
    @POST("v1/app/route/list")
    Observable<JSONObject> cardRouteList(@Body Map<String ,Object> route);



    /**
     * 骑行数据统计
     * @param route
     * @return
     */
    @POST("v1/app/cyclingStatics/track_statics")
    Observable<JSONObject> cardRunAllData(@Body Map<String ,Object> route);


}
