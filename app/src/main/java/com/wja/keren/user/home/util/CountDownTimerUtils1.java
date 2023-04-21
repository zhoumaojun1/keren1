package com.wja.keren.user.home.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.wja.keren.R;
import com.wja.keren.user.home.main.HomeTabActivity;
import com.wja.keren.zxing.util.IntentUtil;

public class CountDownTimerUtils1 extends CountDownTimer {
    private TextView mTextView;
    private Activity activity;
    public CountDownTimerUtils1(Activity activity,TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        this.activity = activity;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText("返回首页" + "(" + millisUntilFinished / 1000 + "S)");  //设置倒计时时间
//        mTextView.setBackgroundResource(R.drawable.shape_ver_code_focus); //设置按钮为灰色，这时是不能点击的
//        SpannableString spannableString = new SpannableString(mTextView.getText().toString());  //获取按钮上的文字
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.WHITE);
//        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
//        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        IntentUtil.get().goActivity(activity, HomeTabActivity.class);
    }


    public void onFinish(Activity activity) {
        IntentUtil.get().goActivity(activity, HomeTabActivity.class);
        mTextView.setClickable(true);//重新获得点击
        mTextView.setBackgroundResource(R.drawable.shape_btn_ver_code);  //还原背景色
    }
}
