package com.wja.keren.user.home.mine.card;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;

public class HelpServiceActivity extends BaseActivity {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_on_line_help_center;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_card_help_service);
    }
}
