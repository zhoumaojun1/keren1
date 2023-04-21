package com.wja.keren.user.home.mine;

import android.view.View;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.zxing.util.IntentUtil;

import butterknife.OnClick;

public class AppInfoActivity extends BaseActivity {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_app_info;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_app_info);
    }

    @OnClick({R.id.rl_app_voice_service,R.id.rl_app_language_settings,R.id.rl_app_feed_back,R.id.rl_app_about_my})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.rl_app_voice_service:
                IntentUtil.get().goActivity(this, VoiceServiceActivity.class);
                break;
            case R.id.rl_app_language_settings:
                IntentUtil.get().goActivity(this, LanguageSettingsActivity.class);
                break;
            case R.id.rl_app_feed_back:
                IntentUtil.get().goActivity(this, EditPhoneSureActivity.class);
                break;
            case R.id.rl_app_about_my:
                IntentUtil.get().goActivity(this, AboutMyActivity.class);
                break;
            default:
                break;
        }
    }
}
