package com.wja.keren.user.home.device;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wja.keren.R;
import com.wja.keren.user.home.login.MainBottomFragment;
import com.wja.keren.user.home.util.AnimationUtils;

public class DeviceBindSuccessFragment extends BottomSheetDialogFragment {
    private static final String TAG = MainBottomFragment.class.getName();
    public static DeviceBindSuccessFragment newInstance() {
        DeviceBindSuccessFragment fragment = new DeviceBindSuccessFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogStyle);
        View  view = View.inflate(getContext(), R.layout.bottom_tab_device_bind, null);
        Button btnCancelBind = view.findViewById(R.id.btn_cancel_bind);
        ImageView ivDisMiss = view.findViewById(R.id.iv_delete_right);
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




        btnCancelBind.setOnClickListener(view1 -> {
            dismiss();

        });
        ivDisMiss.setOnClickListener(view1 -> {
            dismiss();

        });

        return bottomSheetDialog;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume"+"弹框执行一次");
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
