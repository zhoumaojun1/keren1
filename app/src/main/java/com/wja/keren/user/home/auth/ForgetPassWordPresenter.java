package com.wja.keren.user.home.auth;

import android.annotation.SuppressLint;
import android.content.Context;

import com.wja.keren.R;
import com.wja.keren.user.Constant;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.network.HtlUserRetrofit;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ForgetPassWordPresenter extends BasePresenterImpl<ForgetPasContact.View> implements ForgetPasContact.Presenter {

    private final String TAG = ForgetPassWordPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    protected final int ACCOUNT_TYPE_EMAIL = 0;
    protected final int ACCOUNT_TYPE_PHONE_NUMBER = 1;
    protected final int ACCOUNT_TYPE_UNKNOWN = -1;

    private boolean accountValid = false;
    private boolean passwordValid = false;
    private int accountType = ACCOUNT_TYPE_UNKNOWN;






    public ForgetPassWordPresenter(Context context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    @Override
    public void FindPassWord(String account, String password) {
        if (account != null && account.length() == 11) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("phone", account);
            HtlRetrofit.getInstance().getService(1).getVerificationCode(hashMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userInfo -> {
                        view.showMessage("验证码获取成功");
                        view.onFinishPasResult("");
                    });

        } else {
            view.showMessage("手机号输入错误");
        }

//        HtlUserRetrofit.getInstance().getService(1).getVerificationCode("",account, password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(userInfo -> {
//                    view.onFinishPasResult("");
//                });

    }
    boolean isVerifySuccess;
    @SuppressLint("CheckResult")
    @Override
    public boolean verificationCodeSuccess(String phone, String code) {
        HashMap<String ,Object>hashMap =new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("code", phone);
        HtlUserRetrofit.getInstance().getService(1).VerificationCode(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    isVerifySuccess = true;
                    view.showMessage("校验验证码成功");
                    view.onFinishPasResult("");
                });

        return isVerifySuccess;

    }

    @Override
    public boolean verificationCode(String password, boolean showError) {
        passwordValid = isPasswordValid(password);

        if (!passwordValid && showError) {
            view.showPasswordError(accountType == ACCOUNT_TYPE_EMAIL ? R.string.password_error : R.string.code_error);
        } else {

            return true;
        }
        return passwordValid;
    }

    private int isAccountValid(String account) {
        if(account.isEmpty()) return ACCOUNT_TYPE_UNKNOWN;
        if(account.matches(Constant.EMAIL_PATTERN)) return ACCOUNT_TYPE_EMAIL;
        if(account.matches(Constant.PHONE_NUMBER_PATTERN)) return ACCOUNT_TYPE_PHONE_NUMBER;
        return ACCOUNT_TYPE_UNKNOWN;
    }

    private boolean isPasswordValid (String password) {
        if(password.isEmpty()) return false;
        return password.matches(Constant.PASSWORD_PATTERN);
    }

}
