package com.wja.keren.user.home.device;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wja.keren.R;
import com.wja.keren.user.home.base.ViewHolder;
import com.wja.keren.user.home.bean.BleScanResultBean;
import com.wja.keren.user.home.login.MainBottomFragment;
import com.wja.keren.user.home.util.AnimationUtils;
import com.wja.keren.user.home.util.CountDownTimerUtils;
import com.wja.keren.user.home.util.CountDownTimerUtils1;

import java.util.ArrayList;
import java.util.List;

public class DeviceListFragment extends BottomSheetDialogFragment {
    private static final String TAG = MainBottomFragment.class.getName();
    CountDownTimerUtils1 mCountDownTimerUtils;
    public static DeviceListFragment newInstance() {
        DeviceListFragment fragment = new DeviceListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogStyle);
        View view = View.inflate(getContext(), R.layout.bottom_tab_device_bind, null);
        ImageView ivMainTabHome = view.findViewById(R.id.iv_delete_right);
        TextView tvBack = view.findViewById(R.id.tv_back_home);
        mCountDownTimerUtils = new CountDownTimerUtils1(getActivity(),tvBack, 3000, 1000);
        mCountDownTimerUtils.start();
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
        ivMainTabHome.setOnClickListener(view1 -> {
            dismiss();
            getActivity().finish();
        });
        return bottomSheetDialog;

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

}
