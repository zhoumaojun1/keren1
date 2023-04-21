package com.wja.keren.user.home.mine.card;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.DemoApplication;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.BaseBean;
import com.wja.keren.user.home.bean.CardInfoBean;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.view.CodeInputView;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddShareAccountActivity extends BaseActivity {

    @BindView(R.id.mCodeInputView)
    CodeInputView codeInputView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_share_account;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setRightText(R.string.submit);
        setToolbarTitle(R.string.mine_add_account_share);
    }

    @Override
    public void onRight(View view) {
        super.onRight(view);
        IntentUtil.get().goActivityResult(this,CardShareInviteActivity.class,codeInputView.getText().toString());
    }
}
