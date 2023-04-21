package com.wja.keren.user.home.mine.card;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;

public class MyTrafficActivity extends BaseActivity {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_traffic_card;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.my_traffic_card_title);
    }
}
