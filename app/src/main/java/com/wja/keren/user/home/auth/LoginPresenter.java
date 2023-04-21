package com.wja.keren.user.home.auth;

import android.content.Context;

import com.wja.keren.R;
import com.wja.keren.user.Constant;
import com.wja.keren.user.home.base.BasePresenterImpl;
import com.wja.keren.user.home.main.HomeTabActivity;
import com.wja.keren.zxing.util.IntentUtil;

public class LoginPresenter extends BasePresenterImpl<LoginContact.View> implements LoginContact.Presenter {

    private final String TAG = LoginPresenter.class.getSimpleName();

    protected final int ACCOUNT_TYPE_EMAIL = 0;
    protected final int ACCOUNT_TYPE_PHONE_NUMBER = 1;
    protected final int ACCOUNT_TYPE_UNKNOWN = -1;

    private boolean accountValid = false;
    private boolean passwordValid = false;
    private int accountType = ACCOUNT_TYPE_UNKNOWN;






    public LoginPresenter(Context context) {
        super(context);
    }




    @Override
    public  void checkAccountValid(String account,boolean showError) {
        if (account.isEmpty()) {
            view.showPasswordError("手机号不能为空");
        } else if (account.length() != 11) {
            view.showPasswordError("手机号不正确");
        } else if (account.matches(Constant.PHONE_NUMBER_PATTERN) && account.length() == 11) {
            view.showPasswordError("手机号正确");
            IntentUtil.get().goActivity(context, HomeTabActivity.class);
        }

        view.showAccountValid(accountType);

    }

    @Override
    public void checkPasswordValid(String password, boolean showError) {
        if (password.isEmpty()) {
            view.showPasswordError("密码不能为空");
        } else {
            passwordValid = isPasswordValid(password);
            view.onLoginResult("密码正确");
            IntentUtil.get().goActivity(context, HomeTabActivity.class);
        }
        if (!passwordValid && showError) {
            view.showPasswordError(accountType == ACCOUNT_TYPE_EMAIL ? R.string.password_error : R.string.code_error);
        }

    }
    @Override
    public void passWordLogin(String account, String password) {
        IntentUtil.get().goActivity(context, HomeTabActivity.class);
//        Disposable dis = HtlRetrofit.getInstance().getService().loginUser(account, password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(userInfo -> {
//                    //调用登录api
//                    view.onLoginResult(userInfo);
//                });

    }
    @Override
    public void verifyCodeLogin(String phone, String code) {
        IntentUtil.get().goActivity(context, HomeTabActivity.class);
//        Disposable dis = HtlRetrofit.getInstance().getService().verificationCode(phone, code)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(userInfo -> {
//                    //调用登录api
//                    view.onLoginResult(userInfo);
//                });
    }

    private boolean isPasswordValid (String password) {
        if(password.isEmpty()) return false;
        return password.matches(Constant.PASSWORD_PATTERN);
    }

}
