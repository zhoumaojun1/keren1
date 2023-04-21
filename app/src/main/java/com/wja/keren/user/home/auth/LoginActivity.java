package com.wja.keren.user.home.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.main.HomeTabActivity;
import com.wja.keren.user.home.util.CountDownTimerUtils;
import com.wja.keren.zxing.util.IntentUtil;

import butterknife.BindView;


/**
 *  User login Activity
 */

public class LoginActivity extends BaseActivity<LoginContact.Presenter> implements LoginContact.View {
    private static final String TAG = LoginActivity.class.getName();
    private boolean agreePrivacyPolicy = true;
    @BindView(R.id.et_password)
     TextInputEditText etPassword;
    @BindView(R.id.et_account)
     TextInputEditText etAccount;
    @BindView(R.id.eil_account)
    TextInputLayout accountLayout;
    @BindView(R.id.til_password)
    TextInputLayout passwordLayout;
    @BindView(R.id.cb_agree)
    RadioButton cbAgree;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPass;
    @BindView(R.id.tv_verify_code)
    TextView tvVerifyCode;
    @BindView(R.id.tv_login_type)
    TextView raLoginType;
    @BindView(R.id.tv_change_login_type)
    TextView raChangeLogin;
    private CountDownTimerUtils mCountDownTimerUtils;

    private boolean isAccountLogin= false;//默认账号登录 为 false
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        presenter = new LoginPresenter(this);
        presenter.attachView(this);
        setCenterIcon(R.mipmap.app_logo_icon);
        etPassword.setHint(getResources().getString(R.string.please_input_password));
        setLeftIcon(R.mipmap.card_back);

        setUpEditTextListeners();
        setUpLogin();
        tvVerifyCode.setOnClickListener((View)->{
            mCountDownTimerUtils = new CountDownTimerUtils(this,tvVerifyCode, 60000, 1000);
            mCountDownTimerUtils.start();
        });
    }



    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_phone_and_verify_login;
    }

    @Override
    protected void init() {

    }

    private void setUpEditTextListeners() {
        etAccount.setOnFocusChangeListener((view, focus) -> {
            if (!focus) {
                presenter.checkAccountValid(etAccount.getText().toString(), true);
            }
        });

        etPassword.setOnFocusChangeListener((view, focus) -> {
            if (!focus) {
                presenter.checkPasswordValid(etPassword.getText().toString(), true);
            }

        });
        etPassword.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                presenter.checkPasswordValid(etPassword.getText().toString(), true);
            }
            return false;
        });
        tvForgetPass.setOnClickListener(view -> {
            IntentUtil.get().goActivity(LoginActivity.this, ForgetPasswordActivity.class);
            this.overridePendingTransition(R.anim.activity_open,0);
        });


        raChangeLogin.setOnClickListener(view -> {
            if (isAccountLogin == false) {
                if (mCountDownTimerUtils != null) {
                    mCountDownTimerUtils.onFinish();
                    mCountDownTimerUtils.cancel();
                }
                isAccountLogin = true;
                tvVerifyCode.setVisibility(View.VISIBLE);
                raChangeLogin.setText("账号登录");
                raLoginType.setText("验证码登录");
                etPassword.setHint(getResources().getString(R.string.forget_pass_input_verification_code));
                etPassword.setTransformationMethod(null);
            } else if (isAccountLogin == true) {
                isAccountLogin = false;
                raChangeLogin.setText("验证码登录");
                etPassword.setHint(getResources().getString(R.string.please_input_password));
                tvVerifyCode.setVisibility(View.GONE);
                raLoginType.setText("账号登录");
            }
        });
    }

    /**
     * login button press
     */
    private void setUpLogin() {
        cbAgree.setOnCheckedChangeListener((compoundButton, checked) -> {
            agreePrivacyPolicy = checked;
        });

        btnLogin.setOnClickListener((v)->{
            presenter.checkAccountValid(etAccount.getText().toString(),true);
            presenter.checkPasswordValid(etPassword.getText().toString(),true);
            if(!agreePrivacyPolicy)
                showError(R.string.agree_privacy_policy);
            if (raLoginType.getText().toString().equals("账号登录")){
                presenter.passWordLogin(etAccount.getText().toString(), etPassword.getText().toString());
            }else {
                presenter.verifyCodeLogin(etAccount.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    /**
     * account is un valid
     * @param type 0 is email, 1 is phone number
     */
    @Override
    public void showAccountValid(int type) {
    }

    /**
     *
     * @param message string resource id
     */
    @Override
    public void showAccountError(int message) {
        accountLayout.setError(getText(message));

    }

    @Override
    public void showPasswordError(String message) {
        accountLayout.setError(message);
    }

    @Override
    public void showPasswordError(int message) {
        accountLayout.setError(getText(message));
    }


    @Override
    public void onLoginResult(Object user) {
        IntentUtil.get().goActivity(LoginActivity.this, HomeTabActivity.class);
    }

    @Override
    public void onVerifyLoginResult(Object user) {
        IntentUtil.get().goActivity(LoginActivity.this,HomeTabActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.cancel();
            mCountDownTimerUtils = null;
        }
    }
}