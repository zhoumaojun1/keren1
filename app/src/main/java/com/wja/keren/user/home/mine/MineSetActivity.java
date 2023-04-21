package com.wja.keren.user.home.mine;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.king.zxing.util.CodeUtils;
import com.king.zxing.util.LogUtils;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.TwoSplashActivity;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.User;
import com.wja.keren.user.home.bean.UserInfoBean;
import com.wja.keren.user.home.bean.UserSetCardBean;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.view.AvatarView;
import com.wja.keren.user.home.view.SwitchButton;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MineSetActivity extends BaseActivity<MineSet.Presenter> implements MineSet.View {
    public static final int REQUEST_CODE_PHOTO = 0X02;

    @BindView(R.id.switch_card_error_shake)
    SwitchButton swSen;

    @BindView(R.id.switch_source_break)
    SwitchButton swLoop;

    @BindView(R.id.switch_user_face_login)
    SwitchButton swFace;

    @BindView(R.id.switch_source_break_monitor)
    SwitchButton swShake;

    @BindView(R.id.switch_source_guard_alarm)
    SwitchButton swLock;
    @BindView(R.id.switch_source_hitch_alarm)
    SwitchButton swMonitor;
    @BindView(R.id.switch_loop_finger_login)
    SwitchButton swGuard;
    @BindView(R.id.iv_change_user_head)
    AvatarView headImg;

    UserInfoBean.User userInfo =new UserInfoBean.User();
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        setToolbarTitle(R.string.mine_user_info);
        setLeftIcon(R.mipmap.card_back);
        presenter = new MineSetPresenter(this);
        presenter.attachView(this);
        if (null != userInfo) {
            Bitmap map = BitmapFactory.decodeFile(userInfo.getPicture());
            headImg.setBitmap(map);
        }


        presenter.getUserConfig();

        swSen.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setUseAiConfig(1, isChecked);
        });
        swLoop.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setUseAiConfig(2, isChecked);
        });
        swFace.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setUseAiConfig(7, isChecked);
        });


        swShake.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setUseAiConfig(3, isChecked);
        });
        swLock.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setUseAiConfig(4, isChecked);
        });
        swMonitor.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setUseAiConfig(5, isChecked);
        });
        swGuard.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setUseAiConfig(6, isChecked);
        });

    }

    @Override
    public void showUserHead(String userHead) {

    }

    @Override
    public void showUserNick(String userNick) {

    }
    int  deviceId1;
    @Override
    public void showConfigInfo(UserSetCardBean cardConfigBean) {
        int   deviceId=cardConfigBean.getData().getId();
        Config.DEVICE_ID =deviceId;
      int  deviceId1 =deviceId;
        System.out.println("获取参数 id====="+cardConfigBean.getData().getId());
        System.out.println("获取参数 异常振动=="+cardConfigBean.getData().getVibration());
        System.out.println("获取参数 切断电源=="+cardConfigBean.getData().getCut_power());
        System.out.println("获取参数 ==充电监测=="+cardConfigBean.getData().getCharge_monitor());
        System.out.println("获取参数 ==防盗报警=="+cardConfigBean.getData().getGuard_alarm());
        System.out.println("获取参数 ==故障消息=="+cardConfigBean.getData().getFault_alarm() );
        System.out.println("获取参数 ==人脸识别=="+cardConfigBean.getData().getFace() );
        System.out.println("获取参数 ==指纹识别=="+cardConfigBean.getData().getFingerprint() );
        swGuard.setChecked(cardConfigBean.getData().getFingerprint() == 1 ? true : false);
        swFace.setChecked(cardConfigBean.getData().getFace() == 1 ? true : false);
        swSen.setChecked(cardConfigBean.getData().getVibration() == 1 ? true : false);
        swLoop.setChecked(cardConfigBean.getData().getCut_power() == 1 ? true : false);
        swShake.setChecked(cardConfigBean.getData().getCharge_monitor() == 1 ? true : false);
        swLock.setChecked(cardConfigBean.getData().getGuard_alarm() == 1 ? true : false);
        swMonitor.setChecked(cardConfigBean.getData().getFault_alarm() == 1 ? true : false);
    }

    UserSetCardBean cardConfigBean;


    @OnClick({R.id.iv_change_user_nick, R.id.iv_change_user_head,
            R.id.iv_change_user_phone, R.id.iv_change_login_password, R.id.btn_out_login})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.iv_change_user_nick:
                IntentUtil.get().goActivityResult(this, EditNickActivity.class, cardConfigBean.getData().getUser_id());
                break;
            case R.id.iv_change_user_phone:
                IntentUtil.get().goActivity(this, EditPhoneActivity.class);
                break;
            case R.id.iv_change_login_password:
                IntentUtil.get().goActivity(this, SetLoginPassWordActivity.class);
                break;
            case R.id.btn_out_login:
                IntentUtil.get().goActivity(this, TwoSplashActivity.class);
                break;
            case R.id.iv_change_user_head:
                startPhotoCode();
                break;

            default:
                break;
        }
    }

    private void startPhotoCode() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_PHOTO:
                    parsePhoto(data);
                    break;
            }

        }
    }

    private void parsePhoto(Intent data) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            headImg.setBitmap(bitmap);
            //异步解析
            asyncThread(() -> {
                 String result = CodeUtils.parseCode(bitmap);
                runOnUiThread(() -> {
//                    presenter.editUserHead(userInfo.getName(),result);
                    presenter.editUserHead("kerenuser_1679984661",result);
                    LogUtils.d("parsePhoto result 照片:" + result);
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                });

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void asyncThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}
