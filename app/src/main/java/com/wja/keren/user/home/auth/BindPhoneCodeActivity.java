package com.wja.keren.user.home.auth;

import android.view.KeyEvent;
import android.widget.Button;
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
 *   绑定手机号 Activity
 */

public class BindPhoneCodeActivity extends BaseActivity<ForgetPasContact.Presenter> implements ForgetPasContact.View {


    @BindView(R.id.et_account)
    TextInputEditText etAccount;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;

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
    //    setUpEditTextListeners();
        setUpBindPhone();
        tvSendVerifyCode.setOnClickListener((View) -> {
            mCountDownTimerUtils = new CountDownTimerUtils(this,tvSendVerifyCode, 60000, 1000);
            mCountDownTimerUtils.start();
        });
    }
    /**
     *  set up account and password input text listener
     */
    private void setUpEditTextListeners() {
        etAccount.setOnFocusChangeListener((view, focus) -> {
            if(!focus) {
                presenter.verificationCode(etAccount.getText().toString(),true);
            }
        });

        etPassword.setOnFocusChangeListener((view,focus) -> {
            if(!focus){
                presenter.verificationCode(etPassword.getText().toString(),true);
            }

        });
        etPassword.setOnKeyListener((view, keyCode, keyEvent) ->{
            if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                presenter.verificationCode(etPassword.getText().toString(), true);
            }
            return false;
        });
    }

    /**
     * login button press
     */
    private void setUpBindPhone() {

        btnForgetPass.setOnClickListener((v)->{
//            presenter.verificationCode(etPassword.getText().toString(),true);
//            presenter.verificationCode(etAccount.getText().toString(),true);
            presenter.FindPassWord(etAccount.getText().toString(),etPassword.getText().toString());

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
        IntentUtil.get().goActivity(BindPhoneCodeActivity.this,ForgetPassNextActivity.class);
        this.overridePendingTransition(R.anim.activity_open,0);
        IntentUtil.get().goActivity(BindPhoneCodeActivity.this, HomeTabActivity.class);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.cancel();
        }
    }
}