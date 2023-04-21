package com.wja.keren.user.home.mine.card;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;

public class MapLocationActivity extends BaseActivity {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_map;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setRightIcon(R.mipmap.card_share_add);
        setToolbarTitle(R.string.card_navigation_location);
    }
}
