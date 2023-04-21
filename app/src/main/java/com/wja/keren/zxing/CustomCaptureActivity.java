/*
 * Copyright (C) 2018 Jenly Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wja.keren.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.zxing.Result;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;
import com.king.zxing.DecodeConfig;
import com.king.zxing.DecodeFormatManager;
import com.king.zxing.ViewfinderView;
import com.king.zxing.analyze.MultiFormatAnalyzer;
import com.king.zxing.config.AspectRatioCameraConfig;
import com.king.zxing.util.CodeUtils;
import com.king.zxing.util.LogUtils;
import com.wja.keren.R;
import com.wja.keren.user.home.device.DeviceListFragment;
import com.wja.keren.user.home.device.PermissionFragment;
import com.wja.keren.user.home.device.ScanCodeResultActivity;
import com.wja.keren.user.home.util.AnimationUtils;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;
import com.wja.keren.zxing.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 自定义继承CaptureActivity
 */
public class CustomCaptureActivity extends CaptureActivity implements AnimationUtils.AnimationListener {
    @BindView(R.id.iv_ble_search_device_ani)
    ImageView iv_ani_device;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;

    @BindView(R.id.tvFlashlight)
    TextView tvFlashlight;

    @BindView(R.id.iv_scan_device)
    RadioButton raScanBing;
    @BindView(R.id.btn_start_ble_search)
    Button btnStartBleSearch;

    @BindView(R.id.tv_point_out)
    TextView tvPointOut;
    @BindView(R.id.root_view)
    ConstraintLayout root_view;


    @BindView(R.id.ivFlashlight)
    ImageView ivFlashlight;
    @BindView(R.id.viewfinderView)
    ViewfinderView viewfinderView;

    @BindView(R.id.previewView)
    PreviewView previewView;


    private boolean isContinuousScan;
    public static final int REQUEST_CODE_PHOTO = 0X02;
    public static final int REQUEST_CODE_SCAN_CODE = 0X01;
    private Unbinder unbinder;
    private Toast toast;
    private RadioButton ivBle;

    boolean isOpenFlashLight = false;//默认 false 关闭灯

    @Override
    public int getLayoutId() {
        return R.layout.custom_capture_activity;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        StatusBarUtils.immersiveStatusBar(this, toolbar, 0.2f);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvRight = findViewById(R.id.tvRight);
        tvTitle.setText(getResources().getString(R.string.scan_code_bind));
        tvRight.setText(getResources().getString(R.string.open_photo_album));


    }

    public void initCameraScan() {
        super.initCameraScan();
        //初始化解码配置
        //初始化解码配置
        DecodeConfig decodeConfig = new DecodeConfig();
        decodeConfig.setHints(DecodeFormatManager.QR_CODE_HINTS)//如果只有识别二维码的需求，这样设置效率会更高，不设置默认为DecodeFormatManager.DEFAULT_HINTS
                .setFullAreaScan(false)//设置是否全区域识别，默认false
                .setAreaRectRatio(0.8f)//设置识别区域比例，默认0.8，设置的比例最终会在预览区域裁剪基于此比例的一个矩形进行扫码识别
                .setAreaRectVerticalOffset(0)//设置识别区域垂直方向偏移量，默认为0，为0表示居中，可以为负数
                .setAreaRectHorizontalOffset(0);//设置识别区域水平方向偏移量，默认为0，为0表示居中，可以为负数

        //在启动预览之前，设置分析器，只识别二维码
        getCameraScan()
                .setCameraConfig(new AspectRatioCameraConfig(this))//设置相机配置，使用 AspectRatioCameraConfig
                .setVibrate(true)
                .setDarkLightLux(45f)//设置光线足够暗的阈值（单位：lux），需要通过{@link #bindFlashlightView(View)}绑定手电筒才有效
                .setBrightLightLux(100f)//设置光线足够明亮的阈值（单位：lux），需要通过{@link #bindFlashlightView(View)}绑定手电筒才有效
                .bindFlashlightView(ivFlashlight)//绑定手电筒，绑定后可根据光线传感器，动态显示或隐藏手电筒按钮
                .setOnScanResultCallback(this)
                .setAnalyzer(new MultiFormatAnalyzer(decodeConfig));//设置分析器,如果内置实现的一些分析器不满足您的需求，你也可以自定义去实现
    }

    @Override
    public boolean onScanResultCallback(Result result) {
        if (isContinuousScan) {
            showToast(result.getText());
        }
        if (null != result.getText() && result.getText().contains("KEREN-EBIKE")) {
            String name = result.getText().substring(result.getText().indexOf(":") + 1);
            String str1 = result.getText().substring(0, result.getText().indexOf(":"));
            String str2 = result.getText().substring(str1.length() + 1, result.getText().length());
                Log.d("截取的字符串==",name);
            Log.d("截取:之后字符串==",str2);
                IntentUtil.get().goActivityResult(this, ScanCodeResultActivity.class, name);

        } else {
            ToastUtils.ToastMessage(this, "非法二维码");
        }

        getCameraScan().setAnalyzeImage(false);
        return isContinuousScan;
    }


    private void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    AnimationUtils animationUtils = new AnimationUtils();

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.iv_ble_device, R.id.iv_scan_device, R.id.btn_start_ble_search})
    void onClickView(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                startPhotoCode();
                break;
            case R.id.ivFlashlight:
                if (isOpenFlashLight == false) {
                    isOpenFlashLight = true;
                    ivFlashlight.setBackgroundResource(R.mipmap.scan_code_take_on);
                    tvFlashlight.setText("关闭手电筒");
                } else {
                    isOpenFlashLight = false;
                    tvFlashlight.setText("打开手电筒");
                    ivFlashlight.setBackgroundResource(R.mipmap.scan_code_take_off);
                }
                break;
            case R.id.iv_ble_device:
                iv_ani_device.setVisibility(View.VISIBLE);
                btnStartBleSearch.setVisibility(View.VISIBLE);
                tvPointOut.setVisibility(View.VISIBLE);
                ivFlashlight.setVisibility(View.INVISIBLE);
                tvTitle.setText("绑定爱车");
                root_view.setBackgroundResource(R.mipmap.main_bg);
                tvRight.setText(getResources().getString(R.string.ble_device_problem));
                tvFlashlight.setVisibility(View.INVISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                viewfinderView.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_scan_device:
                tvFlashlight.setVisibility(View.VISIBLE);
                tvRight.setText(getResources().getString(R.string.open_photo_album));
                btnStartBleSearch.setVisibility(View.INVISIBLE);
                tvPointOut.setVisibility(View.GONE);
                ivFlashlight.setVisibility(View.VISIBLE);
                tvTitle.setText("扫码绑定");
                tvRight.setVisibility(View.VISIBLE);
                iv_ani_device.setVisibility(View.INVISIBLE);
                animationUtils.startAnimation(iv_ani_device, 1);
                viewfinderView.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_start_ble_search:
                animationUtils.startAnimation(iv_ani_device, 2);
                showBottomSheetDialog();
                break;

            default:
                break;

        }

    }

    private void showBottomSheetDialog() {
        PermissionFragment fragment = PermissionFragment.newInstance();
        fragment.show(getSupportFragmentManager(), PermissionFragment.class.getSimpleName());
    }

    private void showBottomDeviceSheetDialog() {
        DeviceListFragment fragment = DeviceListFragment.newInstance();
        fragment.show(getSupportFragmentManager(), DeviceListFragment.class.getSimpleName());
    }

    private void startPhotoCode() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_PHOTO:
                    parsePhoto(data);
                    break;
                case REQUEST_CODE_SCAN_CODE:
                    parsePhoto(data);
                    break;

            }

        }
    }

    private void parsePhoto(Intent data) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            //异步解析
            asyncThread(() -> {
                String result = CodeUtils.parseCode(bitmap);
                if (null != result && result.startsWith("KEREN-EBIKE")) {
                    String name = result.substring(result.indexOf(":") + 1);
                    String str1 = result.substring(0, result.indexOf(":"));
                    String str2 = result.substring(str1.length() + 1, result.length());
                    Log.d("截取的字符串==",name);
                    Log.d("截取:之后字符串==",str2);
                    Log.d("截取的字符串 parsePhoto==",name + "截取的字符串length =="+name.length() );
                    Log.d("截取的字符串 parsePhoto==",name + "截取的字符串length =="+str2.length() );
                        IntentUtil.get().goActivityResult(this, ScanCodeResultActivity.class, name);

                } else {
                    ToastUtils.ToastMessage(this, "非法二维码");
                }

                runOnUiThread(() -> {
                    LogUtils.d("result:" + result);
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

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
        unbinder.unbind();

    }

    @Override
    public void onFinish() {

    }
}