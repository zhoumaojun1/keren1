package com.wja.keren.user.home.mine;

import android.view.View;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.mine.card.OnlineRepairActivity;
import com.wja.keren.zxing.util.IntentUtil;

import butterknife.OnClick;

public class HelpAndServiceActivity extends BaseActivity {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_mine_help;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_card_help_service);

    }

    @OnClick({R.id.rl_help_other})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.rl_help_other:
                IntentUtil.get().goActivity(this, AppInfoActivity.class);
                break;

            default:
                break;
        }
    }
}
