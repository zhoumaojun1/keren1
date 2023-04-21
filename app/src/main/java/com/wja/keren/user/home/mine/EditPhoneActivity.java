package com.wja.keren.user.home.mine;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.wja.keren.R;
import com.wja.keren.user.home.TwoSplashActivity;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.network.HtlUserRetrofit;
import com.wja.keren.user.home.util.CountDownTimerUtils;
import com.wja.keren.user.home.view.SwitchButton;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditPhoneActivity extends BaseActivity {
    @BindView(R.id.et_account)
    TextInputEditText etAccount;

    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.tvSendVerifyCode)
    TextView tvSendCode;

    private CountDownTimerUtils mCountDownTimerUtils;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_change_phone_code;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_set_edit_phone_code);
    }

    @OnClick({R.id.btn_forget_pass_next, R.id.tvSendVerifyCode})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.btn_forget_pass_next:
                if (etAccount.getText().length() == 11 && etPassword.getText().length() == 6) {
                    changePassWord(etAccount.getText().toString(), etPassword.getText().toString());
                } else {
                    ToastUtils.ToastMessage(this, "密码或者验证码格式错误");
                }


                break;
            case R.id.tvSendVerifyCode:
                verifyCode();
                break;


            default:
                break;
        }
    }


    @SuppressLint("CheckResult")
    private void verifyCode() {
        if (etAccount.getText().toString() != null && etAccount.getText().toString().length() == 11) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("phone", etAccount.getText().toString());
            HtlRetrofit.getInstance().getService(1).getVerificationCode(hashMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userInfo -> {
                        showMessage("验证码获取成功");
                    });
            mCountDownTimerUtils = new CountDownTimerUtils(this,tvSendCode, 60000, 1000);
            mCountDownTimerUtils.start();
        } else {
            ToastUtils.ToastMessage(this, "手机号输入错误");
        }


    }

    @SuppressLint("CheckResult")
    public void changePassWord(String account, String code) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", account);
        hashMap.put("code",Integer.parseInt(code));
        HtlUserRetrofit.getInstance().getService(1).changePhonePassword(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    IntentUtil.get().goActivity(this, TwoSplashActivity.class);
                    showMessage("更换手机号修改成功");
                });
    }

}
