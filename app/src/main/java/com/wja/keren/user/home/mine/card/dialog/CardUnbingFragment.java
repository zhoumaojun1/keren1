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
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.bean.CardUnBindBean;
import com.wja.keren.user.home.device.DeviceBindFragment;
import com.wja.keren.user.home.login.MainBottomFragment;
import com.wja.keren.user.home.main.HomeTabActivity;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.util.AnimationUtils;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardUnbingFragment extends BottomSheetDialogFragment {
    private static final String TAG = MainBottomFragment.class.getName();

    public static CardUnbingFragment newInstance() {
        CardUnbingFragment fragment = new CardUnbingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogStyle);
        View view = View.inflate(getContext(), R.layout.dialog_card_unbind, null);
        TextView tvCancel = view.findViewById(R.id.btnCancel);
        TextView tvConfirm = view.findViewById(R.id.btnConfirm);
        AnimationUtils.slideToUp(getActivity(), view);
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

        AnimationUtils.slideToUp1(getActivity(), view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);//屏幕高的90%
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);


        tvCancel.setOnClickListener(view1 -> {
            dismiss();
        });
        tvConfirm.setOnClickListener(view1 -> {
            dismiss();
            cardUnBind(Config.DEVICE_ID);
        });
        return bottomSheetDialog;

    }

    @SuppressLint("CheckResult")
    void cardUnBind(int id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        HtlRetrofit.getInstance().getService(2).cardUnBind(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        CardUnBindBean cardList = JSONObject.parseObject(String.valueOf(cardInfo), CardUnBindBean.class);
                        if (cardList != null && "ok".equals(cardList.getMsg()) || 0 == cardList.getCode()) {
                            if (null != getActivity()) {
                                IntentUtil.get().goActivity(getActivity(), HomeTabActivity.class);
                            }
                        } else {
                            Log.d("Unbind error==", cardList.getMsg() + cardList.getCode());
                        }
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
