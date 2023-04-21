package com.wja.keren.user.home.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wja.keren.R;
import com.wja.keren.user.Constant;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.auth.ForgetPasswordActivity;
import com.wja.keren.user.home.auth.LoginActivity;
import com.wja.keren.user.home.base.BaseView;
import com.wja.keren.user.home.bean.LoginInfoBean;
import com.wja.keren.user.home.bean.VerifyCodeBean;
import com.wja.keren.user.home.main.HomeTabActivity;
import com.wja.keren.user.home.main.SelectRoleActivity;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.network.HtlUserRetrofit;
import com.wja.keren.user.home.util.AnimationUtils;
import com.wja.keren.user.home.util.CountDownTimerUtils;
import com.wja.keren.user.home.util.SPUtils;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginAndRegisterDialog extends BottomSheetDialogFragment implements BaseView {
    private static final String TAG = LoginActivity.class.getName();
    private boolean agreePrivacyPolicy = false;
    EditText etPassword;
    EditText etAccount;
    LinearLayout accountLayout;
    RelativeLayout passwordLayout;

    ImageView ivDeleText;
    ImageView ivCloseEyes;
    private ImageView cbAgree;
    Button btnLogin;
    TextView tvForgetPass;
    TextView tvVerifyCode;
    TextView raLoginType;
    TextView raChangeLogin;
    private CountDownTimerUtils mCountDownTimerUtils;
    private boolean isAccountLogin = true;//默认账号登录 为 true
    private boolean passwordValid = false;


    protected final int ACCOUNT_TYPE_EMAIL = 0;
    protected final int ACCOUNT_TYPE_PHONE_NUMBER = 1;
    protected final int ACCOUNT_TYPE_UNKNOWN = -1;
    private int accountType = ACCOUNT_TYPE_UNKNOWN;
    private List<String> userRoles = new ArrayList<>();

    public static LoginAndRegisterDialog newInstance() {
        LoginAndRegisterDialog fragment = new LoginAndRegisterDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @SuppressLint("CheckResult")
    @Override
    public void onResume() {
        super.onResume();

        tvVerifyCode.setOnClickListener((View) -> {
            if (etAccount.getText().toString() != null && etAccount.getText().toString().length() == 11) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("phone", etAccount.getText().toString());
                HtlRetrofit.getInstance().getService(1).getVerificationCode(hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(userInfo -> {
                            showMessage("验证码获取成功");
                        });
                mCountDownTimerUtils = new CountDownTimerUtils(getActivity(), tvVerifyCode, 60000, 1000);
                mCountDownTimerUtils.start();
            } else {
                ToastUtils.ToastMessage(getActivity(), "手机号输入错误");
            }

        });


    }


    @SuppressLint("ResourceAsColor")
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog bottomSheet = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialog);
        View view = View.inflate(getContext(), R.layout.activity_phone_and_verify_login, null);
        passwordLayout = view.findViewById(R.id.til_password);
        etPassword = view.findViewById(R.id.et_password);
        etAccount = view.findViewById(R.id.et_account);
        cbAgree = view.findViewById(R.id.cb_agree);
        btnLogin = view.findViewById(R.id.btn_login);
        ivDeleText = view.findViewById(R.id.iv_delete_text);
        ivCloseEyes = view.findViewById(R.id.iv_close_eyes);
        tvForgetPass = view.findViewById(R.id.tv_forget_password);
        tvVerifyCode = view.findViewById(R.id.tv_verify_code);
        raLoginType = view.findViewById(R.id.tv_login_type);
        raChangeLogin = view.findViewById(R.id.tv_change_login_type);
        etPassword.setHint(getResources().getString(R.string.please_input_password));
        setUpEditTextListeners();
        setUpLogin();
        bottomSheet.setContentView(view);
        bottomSheet.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundColor(android.R.color.transparent);
        AnimationUtils.slideToUp1(getActivity(), view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.9);//屏幕高的90%
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
        return bottomSheet;

    }

    /**
     * 计算高度(初始化可以设置默认高度)
     *
     * @return
     */
    private int getWindowHeight() {
        Resources res = this.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        //设置弹窗高度为屏幕高度的3/4
        return heightPixels - heightPixels / 5;
    }

    boolean isOpenEyes = false;

    private void setUpEditTextListeners() {
        ivDeleText.setOnClickListener(view -> {

            etAccount.setText("");
        });
        ivCloseEyes.setOnClickListener(view -> {
            if (isOpenEyes == false) {
                isOpenEyes = true;
                ivCloseEyes.setBackgroundResource(R.mipmap.open_eyes);
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见

            } else {

                isOpenEyes = false;
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码不可见
                ivCloseEyes.setBackgroundResource(R.mipmap.close_eyes);

            }

        });

        etPassword.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

            }
            return false;
        });
        tvForgetPass.setOnClickListener(view -> {
            IntentUtil.get().goActivity(getActivity(), ForgetPasswordActivity.class);

        });

        raChangeLogin.setOnClickListener(view -> {
            if (isAccountLogin == true) {
                if (mCountDownTimerUtils != null) {
                    mCountDownTimerUtils.onFinish();
                    mCountDownTimerUtils.cancel();
                }
                isAccountLogin = false;
                tvVerifyCode.setVisibility(View.VISIBLE);
                raChangeLogin.setText("账号登录");
                raLoginType.setText("验证码登录");
                tvForgetPass.setVisibility(View.GONE);
                etPassword.setHint(getResources().getString(R.string.forget_pass_input_verification_code));
                ivCloseEyes.setVisibility(View.GONE);

            } else {
                isAccountLogin = true;
                raChangeLogin.setText("验证码登录");
                ivCloseEyes.setVisibility(View.VISIBLE);
                tvForgetPass.setVisibility(View.VISIBLE);
                etPassword.setHint(getResources().getString(R.string.please_input_password));
                tvVerifyCode.setVisibility(View.GONE);
                raLoginType.setText("账号登录");
            }
        });
    }

    boolean isAgree = false;

    /**
     * login button press
     */
    @SuppressLint("CheckResult")
    private void setUpLogin() {
//        cbAgree.setOnCheckedChangeListener((compoundButton, checked) -> {
//            agreePrivacyPolicy = checked;
//        });


        cbAgree.setOnClickListener(view -> {
            if (isAgree == false) {
                isAgree = true;
                cbAgree.setBackgroundResource(R.mipmap.agree_fault);
                agreePrivacyPolicy = false;
            } else {
                isAgree = false;
                agreePrivacyPolicy = true;
                cbAgree.setBackgroundResource(R.mipmap.done);
            }

        });

        btnLogin.setOnClickListener((v) -> {
            boolean isVerifyOk = checkPasswordValid(etAccount.getText().toString(), etPassword.getText().toString());
            if (isVerifyOk) {
                userLogin("", etAccount.getText().toString(), etPassword.getText().toString(), "", 0);
            }
        });
    }

    public boolean checkPasswordValid(String phone, String password) {
        if (password.isEmpty() && phone.isEmpty()) {
            ToastUtils.ToastMessage(getActivity(), "手机和密码不能为空");
            return false;
        } else {
            if (phone.length() != 11) {
                ToastUtils.ToastMessage(getActivity(), "手机号错误");
                return false;
            } else if (phone.length() == 11 && phone.matches(Constant.PHONE_NUMBER_PATTERN)) {
                passwordValid = isPasswordValid(password);
                if (!passwordValid) {
                    ToastUtils.ToastMessage(getActivity(), getResources().getString(R.string.error_account_password_point));
                } else {
                    passwordValid = true;
                }
            }
        }
        return passwordValid;
    }

    @SuppressLint("CheckResult")
    public void userLogin(String code, String phone, String password, String wx_id, int type) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (!agreePrivacyPolicy) {
            ToastUtils.ToastMessage(getActivity(), getResources().getString(R.string.agree_privacy_policy));
        } else {
            if (raLoginType.getText().toString().equals("账号登录")) {
                hashMap.put("phone", phone);
                hashMap.put("password", password);
                hashMap.put("type", 0);
//                    hashMap.put("phone", phone);
//                    hashMap.put("wx_id", wx_id);
//                    hashMap.put("type", 2);
                HtlUserRetrofit.getInstance().getService(1).loginUser(
                                hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(userInfo -> {
                            if (userInfo != null) {
                                LoginInfoBean user = JSONObject.parseObject(String.valueOf(userInfo), LoginInfoBean.class);
                                if (null != user && user.getCode() == 0 && user.getMsg().equals("ok")) {
                                    LoginInfoBean.UserInfo userInfoBean = user.getData();
                                    SPUtils.put("token", userInfoBean.getToken(), true);
                                    Config.USER_TOKEN = userInfoBean.getToken();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("userInfo", userInfoBean);
                                    Intent intent = new Intent();
                                    intent.putExtras(bundle);
                                    userRoles = userInfoBean.getRole_names();
                                    if (userRoles != null && userRoles.size() == 0) {
                                        IntentUtil.get().goActivityResult(getActivity(), HomeTabActivity.class, intent);
                                    } else {
                                        IntentUtil.get().goActivityResult(getActivity(), SelectRoleActivity.class, intent);
                                    }

                                } else if (null != user && user.getCode() == 1103) {
                                    ToastUtils.ToastMessage(getActivity(), "你输入的密码错误");
                                }
                            }

                        });
            } else {
                hashMap.put("phone", phone);
                hashMap.put("code", Integer.parseInt(password));

                HtlRetrofit.getInstance().getService(1).VerificationCode(hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(verifyCode -> {
                            if (verifyCode != null) {
                                VerifyCodeBean verifyCodeBean = JSONObject.parseObject(String.valueOf(verifyCode), VerifyCodeBean.class);
                                if (verifyCodeBean.getCode() == 200 || "ok".equals(verifyCodeBean.getMsg())) {
                                    showMessage("验证码登录成功");
                                    IntentUtil.get().goActivity(getActivity(), HomeTabActivity.class);
                                } else {
                                    ToastUtils.ToastMessage(getActivity(), "验证码输入错误");
                                }
                            }
                        });
            }
        }

    }


    public void checkAccountValid(String account, boolean showError) {
        accountType = isAccountValid(account);
        if (accountType == -1 && showError) {
            showError(R.string.account_error);
            return;
        }
    }

    public void checkPasswordValid(String password, boolean showError) {
        passwordValid = isPasswordValid(password);

        if (!passwordValid && showError) {
            showError(accountType == ACCOUNT_TYPE_EMAIL ? R.string.password_error : R.string.code_error);
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


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showError(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    ProgressDialog dialog;

    @Override
    public void showDialog(String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    @Override
    public void dismissDialog() {

    }
}
