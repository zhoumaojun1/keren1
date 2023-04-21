package com.wja.keren.user.home.mine.card;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.login.LoginAndRegisterDialog;
import com.wja.keren.user.home.mine.card.dialog.TimingSelectFragment;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.network.HtlUserRetrofit;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardShareInviteActivity extends BaseActivity {

    @BindView(R.id.tv_other_user)
    TextView tvOtherUser;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_card_share_invite;
    }
    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_send_share_invite);
        tvOtherUser.setText(getIntent().getStringExtra("bundle"));
    }
    @OnClick({R.id.tv_time_selection_value,R.id.btn_send_share_device})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.tv_time_selection_value:
                showBottomCardTimeSheetDialog(getIntent().getStringExtra("bundle"));
                break;
            case R.id.btn_send_share_device:
                sendShareDevice();
                break;

            default:
                break;
        }
    }


    static String shareTime;
    public static void submit(String phone,String time){
         shareTime = time;
        Log.d("submit",phone+time);

     }
    @SuppressLint("CheckResult")
     private void sendShareDevice(){
        HashMap<String, Object> hashMap = new HashMap<>();
        if (shareTime!=null) {
            hashMap.put("share_time", shareTime);
        } else {
            hashMap.put("share_time", -1);
        }
         hashMap.put("ebike_id", Config.DEVICE_ID);
         hashMap.put("phone",getIntent().getStringExtra("bundle"));
         HtlRetrofit.getInstance().getService(2).addCardShareAccount(
                         hashMap)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(cardInfo -> {
                     if (cardInfo != null) {
                         ToastUtils.ToastMessage(this,"分享设备成功");
                     }
                 });
     }
    private void showBottomCardTimeSheetDialog(String userPhone) {
        TimingSelectFragment fragment = TimingSelectFragment.newInstance(userPhone);
        fragment.show(getSupportFragmentManager(), TimingSelectFragment.class.getSimpleName());
    }
}
