package com.wja.keren.user.home.auth;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.main.HomeTabActivity;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.OnClick;


public class twoSplashActivity extends BaseActivity {
    @BindView(R.id.btn_login_register)
     Button btnLogin;
    @BindView(R.id.btnVirtual)
     Button btnVirtual;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash_two;
    }

    @Override
    protected void init() {
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
                Toast.makeText(twoSplashActivity.this,errString.toString(), Toast.LENGTH_SHORT).show();
            }
            //指纹验证成功
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(twoSplashActivity.this, "指纹验证成功", Toast.LENGTH_SHORT).show();
                // IntentUtil.get().goActivity(twoSplashActivity.this, HomeActivity.class);
                IntentUtil.get().goActivity(twoSplashActivity.this, HomeTabActivity.class);
            }
            //指纹验证失败
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(twoSplashActivity.this,"指纹验证失败", Toast.LENGTH_SHORT).show();
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
                startHomeActivity1();
                break;
            default:
                break;
        }
    }
    void startHomeActivity(){
        IntentUtil.get().goActivity(twoSplashActivity.this, LoginActivity.class);
        overridePendingTransition(0, 0);
    }
    void startHomeActivity1(){
        IntentUtil.get().goActivity(twoSplashActivity.this, HomeTabActivity.class);
        overridePendingTransition(0, 0);
    }
}