package com.wja.keren.user.home.mine.card;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.mine.EditPhoneSureActivity;
import com.wja.keren.user.home.network.HtlUserRetrofit;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardNameEditActivity extends BaseActivity {
    @BindView(R.id.etCardName)
    EditText etInputName;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_card_name_edit;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_card_name_setting);
        setRightText(R.string.submit);
    }

    @Override
    public void onRight(View view) {
        super.onRight(view);
        ToastUtils.ToastMessage(this,getIntent().getIntExtra(("bundle"),0)+"");
        editNick(getIntent().getIntExtra(("bundle"),0),etInputName.getText().toString());

    }
    @SuppressLint("CheckResult")
    public void editNick(int userId, String cardName) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId",userId);
        hashMap.put("cardName", cardName);
        HtlUserRetrofit.getInstance().getService(1).changePhonePassword(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    IntentUtil.get().goActivity(this, EditPhoneSureActivity.class);
                    showMessage("昵称修改成功");
                });
    }

}
