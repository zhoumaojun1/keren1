package com.wja.keren.user.home.mine;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;

public class VoiceSetActivity extends BaseActivity {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_app_info;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_language_settings);
    }
}
