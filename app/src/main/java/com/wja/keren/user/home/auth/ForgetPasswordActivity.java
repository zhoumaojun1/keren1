package com.wja.keren.user.home.auth;

import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.util.CountDownTimerUtils;
import com.wja.keren.zxing.util.IntentUtil;

import butterknife.BindView;


/**
 *  User ForgetPass Activity
 */

public class ForgetPasswordActivity extends BaseActivity<ForgetPasContact.Presenter> implements ForgetPasContact.View {


    @BindView(R.id.et_phone)
    TextInputEditText etPhone;
    @BindView(R.id.et_verification_code)
    TextInputEditText etVerificationCode;

    @BindView(R.id.eil_account)
    TextInputLayout accountLayout;
    @BindView(R.id.til_password)
    TextInputLayout passwordLayout;

    @BindView(R.id.btn_forget_pass_next)
    Button btnForgetPass;

    @BindView(R.id.tvSendVerifyCode)
    TextView tvSendVerifyCode;

    CountDownTimerUtils mCountDownTimerUtils;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void init() {
        setFullScreen();
        presenter = new ForgetPassWordPresenter(this);
        presenter.attachView(this);
        setLeftIcon(R.mipmap.card_back);
        setCenterIcon(R.mipmap.app_logo_icon);
        setUpEditTextListeners();
        setUpLogin();
        tvSendVerifyCode.setOnClickListener((View) -> {
            mCountDownTimerUtils = new CountDownTimerUtils(this,tvSendVerifyCode, 60000, 1000);
            mCountDownTimerUtils.start();
        });
    }
    /**
     *  set up account and password input text listener
     */
    private void setUpEditTextListeners() {
        etVerificationCode.setOnClickListener(view -> {
           presenter.verificationCodeSuccess(etPhone.getText().toString(),etVerificationCode.getText().toString());
        });

    }

    /**
     * login button press
     */
    private void setUpLogin() {
        btnForgetPass.setOnClickListener((v)->{
            IntentUtil.get().goActivity(ForgetPasswordActivity.this,ForgetPassNextActivity.class);

        });
    }

    /**
     * account is un valid
     * @param type 0 is email, 1 is phone number
     */
    @Override
    public void showAccountValid(int type) {
    }



    @Override
    public void showPasswordError(int message) {
        passwordLayout.setError(getText(message));
    }

    @Override
    public void onFinishPasResult(String user) {
        IntentUtil.get().goActivity(this,ForgetPassNextActivity.class);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.cancel();
        }
    }
}