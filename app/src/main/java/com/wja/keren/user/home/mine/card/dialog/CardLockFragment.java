package com.wja.keren.user.home.mine.card.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.bean.CardUnBindBean;
import com.wja.keren.user.home.device.DeviceBindFragment;
import com.wja.keren.user.home.login.MainBottomFragment;
import com.wja.keren.user.home.mine.card.CardSettingActivity;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.util.AnimationUtils;
import com.wja.keren.user.home.view.ToastUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardLockFragment extends BottomSheetDialogFragment {
    private static final String TAG = MainBottomFragment.class.getName();

    public static CardLockFragment newInstance(String status) {
        CardLockFragment fragment = new CardLockFragment();
        Bundle args = new Bundle();
        args.putString("cardStatus",status);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogStyle);
        View  view = View.inflate(getContext(), R.layout.dialog_setting_card_lock, null);
         String cardStatus = getArguments().getString("cardStatus");
        TextView btnCancel = view.findViewById(R.id.btnCancel);
        TextView btnConfirm = view.findViewById(R.id.btnConfirm);
        AnimationUtils.slideToUp(getActivity(),view);
        if (bottomSheetDialog.getWindow() != null) {//灰色的阴影效果去除
            WindowManager.LayoutParams params = bottomSheetDialog.getWindow().getAttributes();
            params.dimAmount = 0.0f;
            bottomSheetDialog.getWindow().setAttributes(params);
        }
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);

        AnimationUtils.slideToUp1(getActivity(),view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);//屏幕高的90%
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);

        btnCancel.setOnClickListener(view1 -> {
            dismiss();
        });
        btnConfirm.setOnClickListener(view1 -> {
            //  dismiss();
            if ("车辆锁定".equals(cardStatus)) {
                cardLock(Config.DEVICE_ID, 0);
            } else if ("车辆解锁".equals(cardStatus)) {
                cardLock(Config.DEVICE_ID, 1);
            }
        });
        return bottomSheetDialog;

    }

    /**
     * 0 解锁 1 锁定
     * @param cardId
     * @param status
     */
    @SuppressLint("CheckResult")
    void cardLock(int cardId,int status ){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("device_id",cardId);
        hashMap.put("status",status);
        HtlRetrofit.getInstance().getService(2).cardLock(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        ToastUtils.ToastMessage(getActivity(),"锁定成功");

                    }
                });
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setClipToOutline(true);
        window.setAttributes(params);
        window.setAllowEnterTransitionOverlap(true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

    }
}
