package com.wja.keren.user.home;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.login.LoginAndRegisterDialog;
import com.wja.keren.user.home.main.HomeTabActivity;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.OnClick;


public class TwoSplashActivity extends BaseActivity {
    @BindView(R.id.btn_login_register)
     Button btnLogin;
    @BindView(R.id.btnVirtual)
     Button btnVirtual;
    private VideoView mVideoView;
    MediaController mMediaController;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash_two;
    }

    @Override
    protected void init() {
        mVideoView = new VideoView(this);
        mVideoView = (VideoView) findViewById(R.id.video);
        mMediaController = new MediaController(this);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.hoshi;
        mVideoView.setVideoURI(Uri.parse(uri));
        mMediaController.setMediaPlayer(mVideoView);
        mMediaController.setVisibility(View.INVISIBLE); //设置隐藏
        mVideoView.setMediaController(mMediaController);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 通过MediaPlayer设置循环播放
                mp.setLooping(true);
                // OnPreparedListener中的onPrepared方法是在播放源准备完成后回调的，所以可以在这里开启播放
                mp.start();
            }
        });

        initfinger();
    }

    private void initfinger() {

        //指纹登录点击事件
        BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()
                .setTitle("指纹登录")
                .setDescription("用户指纹验证")
                .setNegativeButtonText("取消")
                .build();
        getprompt().authenticate(promptInfo);
    }
    //我这里写了一个方法，也可以不写，直接把这个里面的代码放在上面的点击事件里也是可以的
    private BiometricPrompt getprompt(){
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback=new BiometricPrompt.AuthenticationCallback() {
            //指纹验证错误
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(TwoSplashActivity.this,errString.toString(), Toast.LENGTH_SHORT).show();
            }
            //指纹验证成功
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(TwoSplashActivity.this, "指纹验证成功", Toast.LENGTH_SHORT).show();
                 IntentUtil.get().goActivity(TwoSplashActivity.this, HomeTabActivity.class);
            }
            //指纹验证失败
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(TwoSplashActivity.this,"指纹验证失败", Toast.LENGTH_SHORT).show();
            }
        };
        BiometricPrompt biometricPrompt=new BiometricPrompt(this,executor,callback);
        return  biometricPrompt;
    }

    @OnClick({R.id.btn_login_register, R.id.btnVirtual})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.btn_login_register:
                startHomeActivity();
                break;
            case R.id.btnVirtual:
                startHomeActivity1(2);
                break;
            default:
                break;
        }
    }
    private void startHomeActivity(){
        showBottomSheetDialog();
    }

    private void showBottomSheetDialog() {
        LoginAndRegisterDialog fragment = LoginAndRegisterDialog.newInstance();
        fragment.show(getSupportFragmentManager(), LoginAndRegisterDialog.class.getSimpleName());
    }
    void startHomeActivity1(int isTag){
        Intent intent = new Intent();
        intent.putExtra("isTag",isTag);
        IntentUtil.get().goActivityResult(TwoSplashActivity.this, HomeTabActivity.class,intent);
    }
}