package com.wja.keren.user.home.mine;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.network.HtlUserRetrofit;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditNickActivity extends BaseActivity {
    @BindView(R.id.etInputNick)
    EditText etInputNick;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_user_nick;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_user_nick);
        setRightText(R.string.submit);

    }

    @Override
    public void onRight(View view) {
        super.onRight(view);
        editNick(getIntent().getStringExtra("bundle"),etInputNick.getText().toString());

    }

    @SuppressLint("CheckResult")
    public void editNick(String userId, String nick) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("account",userId);
        hashMap.put("nick", nick);
        HtlUserRetrofit.getInstance().getService(1).changePhonePassword(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    IntentUtil.get().goActivity(this, EditPhoneSureActivity.class);
                    showMessage("昵称修改成功");
                });
    }
}
