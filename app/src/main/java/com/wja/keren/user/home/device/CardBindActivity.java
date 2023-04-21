package com.wja.keren.user.home.device;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.view.FrameAnimationManager;

import butterknife.BindView;

public class CardBindActivity extends BaseActivity {
    @BindView(R.id.iv_ani_device)
    ImageView iv_ani_device;

    @BindView(R.id.ra_scan_device)
    RadioButton raScanBing;
    @BindView(R.id.btn_start_ble_search)
    Button btnStartBleSearch;

//    @BindView(R.id.iv_ble_search_device)
//    Button ivBleSearchDevice;

    private ImageView animationImg;//播放动画的背景
    private FrameAnimationManager.FramesAnimation framesAnimation;
    //动画图片ID数组
    public static int[] imgArr = {R.mipmap.ani_01, R.mipmap.ani_02, R.mipmap.ani_03, R.mipmap.ani_04, R.mipmap.ani_05, R.mipmap.ani_06, R.mipmap.ani_07, R.mipmap.ani_08, R.mipmap.ani_09, R.mipmap.ani_10,
            R.mipmap.ani_11, R.mipmap.ani_12, R.mipmap.ani_13, R.mipmap.ani_14, R.mipmap.ani_15, R.mipmap.ani_16, R.mipmap.ani_17, R.mipmap.ani_18, R.mipmap.ani_19, R.mipmap.ani_20,
            R.mipmap.ani_21, R.mipmap.ani_22, R.mipmap.ani_23, R.mipmap.ani_24, R.mipmap.ani_25, R.mipmap.ani_26, R.mipmap.ani_27, R.mipmap.ani_28, R.mipmap.ani_29, R.mipmap.ani_30,
            R.mipmap.ani_31, R.mipmap.ani_32, R.mipmap.ani_33, R.mipmap.ani_34, R.mipmap.ani_35, R.mipmap.ani_36, R.mipmap.ani_37, R.mipmap.ani_38, R.mipmap.ani_39, R.mipmap.ani_40,
            R.mipmap.ani_41, R.mipmap.ani_42, R.mipmap.ani_43, R.mipmap.ani_44, R.mipmap.ani_45, R.mipmap.ani_46, R.mipmap.ani_47, R.mipmap.ani_48, R.mipmap.ani_49, R.mipmap.ani_50,
            R.mipmap.ani_51, R.mipmap.ani_52, R.mipmap.ani_53, R.mipmap.ani_54, R.mipmap.ani_55, R.mipmap.ani_56, R.mipmap.ani_57, R.mipmap.ani_58, R.mipmap.ani_59, R.mipmap.ani_60};

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_ble_scan_device;
    }

    @Override
    protected void init() {
        setToolbarTitle("蓝牙扫码设备");
        setLeftIcon(R.mipmap.card_back);
        framesAnimation = FrameAnimationManager.getInstance().createFramesAnimation();
        //播放动画
        startAnimation(imgArr,20);
        setOnclick();
    }
    //播放动画
    private void startAnimation(int[] frames, int delay) {
        if (framesAnimation.isRunning()) {
            framesAnimation.stop();
        }
        framesAnimation.setFrameData(iv_ani_device, frames, delay, true);
        framesAnimation.start();
        showBottomSheetDialog();
    }
    private void showBottomSheetDialog() {
        PermissionFragment fragment = PermissionFragment.newInstance();
        fragment.show(getSupportFragmentManager(), PermissionFragment.class.getSimpleName());
    }
    void setOnclick(){
        raScanBing.setOnClickListener(view -> {
          finish();
        });
    }
}
