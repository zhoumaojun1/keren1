package com.wja.keren.user.home.auth;

import android.annotation.SuppressLint;
import android.content.Context;

import com.wja.keren.R;
import com.wja.keren.user.Constant;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.main.HomeTabActivity;
import com.wja.keren.user.home.network.HtlUserRetrofit;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForgetPassNextPresenter extends BasePresenterImpl<ForgetPasNextContact.View> implements ForgetPasNextContact.Presenter {


    protected final int ACCOUNT_TYPE_EMAIL = 0;
    protected final int ACCOUNT_TYPE_PHONE_NUMBER = 1;
    protected final int ACCOUNT_TYPE_UNKNOWN = -1;

    private boolean accountValid = false;
    private boolean passwordValid = false;
    private int accountType = ACCOUNT_TYPE_UNKNOWN;


    public ForgetPassNextPresenter(Context context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    @Override
    public void findPassWord(String code, String oldPassword, String newPassword) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("password_old", oldPassword);
        hashMap.put("password_new", newPassword);
        HtlUserRetrofit.getInstance().getService(1).editPassword(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    view.showMessage("密码修改成功");
                    view.onFinishPasResult("userInfo");
                });
    }

    @Override
    public boolean checkPasswordValid(String password, String newPassword, boolean showError) {
        checkPasswordForm(password, true);
        if (password.isEmpty() || newPassword.isEmpty()) {
            view.showError(context.getString(R.string.login_input_password));
            return false;
        } else if (!password.equals(newPassword)) {
            view.showError(context.getString(R.string.once_input_pass_inconformity));
            return false;
        } else if (password.equals(newPassword)) {
//            view.showMessage(context.getString(R.string.password_code_login));
            return true;

        }
        return false;

    }

    @Override
    public void checkPasswordForm(String password, boolean showError) {
        passwordValid = isPasswordValid(password);
        if (!passwordValid && showError) {
            view.showPasswordError(accountType == ACCOUNT_TYPE_EMAIL ? R.string.password_error : R.string.code_error);
        }

    }

    private int isAccountValid(String account) {
        if (account.isEmpty()) return ACCOUNT_TYPE_UNKNOWN;
        if (account.matches(Constant.EMAIL_PATTERN)) return ACCOUNT_TYPE_EMAIL;
        if (account.matches(Constant.PHONE_NUMBER_PATTERN)) return ACCOUNT_TYPE_PHONE_NUMBER;
        return ACCOUNT_TYPE_UNKNOWN;
    }

    private boolean isPasswordValid(String password) {
        if (password.isEmpty()) return false;
        return password.matches(Constant.PASSWORD_PATTERN);
    }

}
