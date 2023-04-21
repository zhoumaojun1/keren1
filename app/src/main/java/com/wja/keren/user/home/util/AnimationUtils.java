package com.wja.keren.user.home.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.wja.keren.R;
import com.wja.keren.user.home.view.FrameAnimationManager;

/**
 * 动画工具类
 * Created by caik on 16/9/22.
 */
public class AnimationUtils {
    public interface AnimationListener{
        void onFinish();
    }
    public AnimationUtils(){

    }

    public static void slideToUp(Context mContext,View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0, 50);
        animator.setDuration(1000);
        animator.start();

    }
    public static void slideToUp1(Context mContext,View view){

        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                view.setX(view.getX());
//                view.setY(view.getY());

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(slide);
    }
    public static void slideToDown(Context context,View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0, 80);
        animator.setDuration(1000);
        animator.start();

    }

    public static void slideToDown1(Context context,View view){
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);

        animation.setDuration(400);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        view.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);

    }
    FrameAnimationManager.FramesAnimation   framesAnimation = FrameAnimationManager.getInstance().createFramesAnimation();
    public static void roundAnimation(Context mContext, ImageView ivAddCard) {
        int[] imgArr = {R.mipmap.ani_charge_00, R.mipmap.ani_charge_01, R.mipmap.ani_charge_01, R.mipmap.ani_charge_01, R.mipmap.ani_charge_01, R.mipmap.ani_charge_01, R.mipmap.ani_charge_01, R.mipmap.ani_charge_01, R.mipmap.ani_charge_01, R.mipmap.ani_charge_10,
                R.mipmap.ani_charge_11, R.mipmap.ani_charge_12, R.mipmap.ani_charge_13, R.mipmap.ani_charge_14, R.mipmap.ani_charge_15, R.mipmap.ani_charge_16, R.mipmap.ani_charge_17, R.mipmap.ani_charge_18, R.mipmap.ani_charge_19, R.mipmap.ani_charge_20,
        };
        FrameAnimationManager.FramesAnimation framesAnimation;
        framesAnimation = FrameAnimationManager.getInstance().createFramesAnimation();
        if (framesAnimation.isRunning()) {
            framesAnimation.stop();
        }
        framesAnimation.setFrameData(ivAddCard, imgArr, 20, true);
        framesAnimation.start();
    }
    //播放动画
    public   void startAnimation(ImageView view,int tag) {
        int[] imgArr = {R.mipmap.ani_01, R.mipmap.ani_02, R.mipmap.ani_03, R.mipmap.ani_04, R.mipmap.ani_05, R.mipmap.ani_06, R.mipmap.ani_07, R.mipmap.ani_08, R.mipmap.ani_09, R.mipmap.ani_10,
                R.mipmap.ani_11, R.mipmap.ani_12, R.mipmap.ani_13, R.mipmap.ani_14, R.mipmap.ani_15, R.mipmap.ani_16, R.mipmap.ani_17, R.mipmap.ani_18, R.mipmap.ani_19, R.mipmap.ani_20,
                R.mipmap.ani_21, R.mipmap.ani_22, R.mipmap.ani_23, R.mipmap.ani_24, R.mipmap.ani_25, R.mipmap.ani_26, R.mipmap.ani_27, R.mipmap.ani_28, R.mipmap.ani_29, R.mipmap.ani_30,
                R.mipmap.ani_31, R.mipmap.ani_32, R.mipmap.ani_33, R.mipmap.ani_34, R.mipmap.ani_35, R.mipmap.ani_36, R.mipmap.ani_37, R.mipmap.ani_38, R.mipmap.ani_39, R.mipmap.ani_40,
                R.mipmap.ani_41, R.mipmap.ani_42, R.mipmap.ani_43, R.mipmap.ani_44, R.mipmap.ani_45, R.mipmap.ani_46, R.mipmap.ani_47, R.mipmap.ani_48, R.mipmap.ani_49, R.mipmap.ani_50,
                R.mipmap.ani_51, R.mipmap.ani_52, R.mipmap.ani_53, R.mipmap.ani_54, R.mipmap.ani_55, R.mipmap.ani_56, R.mipmap.ani_57, R.mipmap.ani_58, R.mipmap.ani_59, R.mipmap.ani_60};
        if (tag == 1 ) {
            framesAnimation.stop();
            return;
        }
        if (framesAnimation.isRunning()) {
            framesAnimation.stop();
        }
        framesAnimation.setFrameData(view, imgArr, 20, true);
        framesAnimation.start();

    }


    public void openCard(){
        //    ValueAnimator valueAnimator;
//    private void startOpenCardAnimation(){
//        if (progressButton.getState() == CProgressButton.STATE.NORMAL) {
//            valueAnimator = ValueAnimator.ofInt(0, 100);
//            valueAnimator.setDuration(5000);
//            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    int value = (int) animation.getAnimatedValue();
//                    state2.setText("state progress:" + value);
//                    if (value == 100) {
//                        progressButton.normal(2, false);
//                        state2.setText("state normal");
//                    }
//                    progressButton.download(value);
//                }
//            });
//            valueAnimator.start();
//        } else {
//            valueAnimator.cancel();
//            progressButton.normal(0, false);
//            state2.setText("state normal");
//        }
//    }

    }
}
