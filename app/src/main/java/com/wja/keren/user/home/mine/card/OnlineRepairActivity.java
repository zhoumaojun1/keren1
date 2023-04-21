package com.wja.keren.user.home.mine.card;

import android.view.View;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.mine.HelpAndServiceActivity;
import com.wja.keren.user.home.mine.MineSetActivity;
import com.wja.keren.zxing.util.IntentUtil;

import butterknife.OnClick;

public class OnlineRepairActivity extends BaseActivity {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_on_line_warranty;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_card_on_line_guarantee);
    }

}
