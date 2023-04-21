package com.wja.keren.user.home.auth;

import android.view.KeyEvent;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.main.HomeTabActivity;
import com.wja.keren.zxing.util.IntentUtil;

import butterknife.BindView;


/**
 *  User ForgetPassNextActivity Activity
 */

public class ForgetPassNextActivity extends BaseActivity<ForgetPasNextContact.Presenter> implements ForgetPasNextContact.View {

    @BindView(R.id.et_new_password)
    TextInputEditText etNewPassword;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    @BindView(R.id.eil_account)
    TextInputLayout accountLayout;
    @BindView(R.id.til_password)
    TextInputLayout passwordLayout;

    @BindView(R.id.btn_login)
    Button btnLogin;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_forget_password_next;
    }

    @Override
    protected void init() {
        setFullScreen();
        presenter = new ForgetPassNextPresenter(this);
        presenter.attachView(this);
        String verifyCode = getIntent().getStringExtra("");
        setLeftIcon(R.mipmap.card_back);
        setCenterIcon(R.mipmap.app_logo_icon);
        setUpFindPassword();
    }



    /**
     * login button press
     */
    private void setUpFindPassword() {
        btnLogin.setOnClickListener((v)->{
            boolean isSuccess =presenter.checkPasswordValid(etPassword.getText().toString(),etNewPassword.getText().toString(),true);
            if (isSuccess == true) {
                presenter.findPassWord("", etPassword.getText().toString(), etNewPassword.getText().toString());
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



    @Override
    public void showPasswordError(int message) {
        passwordLayout.setError(getText(message));
    }

    @Override
    public void onFinishPasResult(String user) {
        IntentUtil.get().goActivity(this, HomeTabActivity.class);

    }


}