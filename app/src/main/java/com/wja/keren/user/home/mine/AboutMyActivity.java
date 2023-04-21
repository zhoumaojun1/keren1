package com.wja.keren.user.home.mine;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;

public class AboutMyActivity extends BaseActivity {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_about_my;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_about_my);
    }
}
