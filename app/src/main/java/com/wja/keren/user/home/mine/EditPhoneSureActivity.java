package com.wja.keren.user.home.mine;

import android.annotation.SuppressLint;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.network.HtlUserRetrofit;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditPhoneSureActivity extends BaseActivity {

    @BindView(R.id.et_old_password)
    TextInputEditText etOldPassword;

    @BindView(R.id.et_new_password)
    TextInputEditText etNewPassword;

    @BindView(R.id.et_again_new_password)
    TextInputEditText etAgainPassword;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_change_phone_code_sure;
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
                if (etNewPassword.getText().toString().equals(etAgainPassword.getText().toString())) {
                    changePassWord(etOldPassword.getText().toString(), etNewPassword.getText().toString(), etAgainPassword.getText().toString());
                } else {
                    ToastUtils.ToastMessage(this, "两次输入的密码不一致");
                }

                break;
            case R.id.tvSendVerifyCode:

            default:
                break;
        }
    }

    @SuppressLint("CheckResult")
    public void changePassWord(String oldPass, String newPass, String againPass) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("password_old", oldPass);
        hashMap.put("password_new", newPass);
        hashMap.put("password_new", againPass);
        HtlUserRetrofit.getInstance().getService(1).changePhonePassword(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    IntentUtil.get().goActivity(this, EditPhoneSureActivity.class);
                    showMessage("密码修改成功");
                });
    }
}
