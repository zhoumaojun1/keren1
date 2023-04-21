package com.wja.keren.user.home.mine.card.dialog;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wja.keren.R;
import com.wja.keren.user.home.bean.CardRunListBean;
import com.wja.keren.user.home.login.MainBottomFragment;
import com.wja.keren.user.home.util.AnimationUtils;
import com.wja.keren.user.home.util.FormatUtil;

public class RunTrackDetailsFragment extends BottomSheetDialogFragment {
    private static final String TAG = MainBottomFragment.class.getName();

    public static RunTrackDetailsFragment newInstance( CardRunListBean.AllList.OneList oneList) {
        RunTrackDetailsFragment fragment = new RunTrackDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("oneList",oneList);
        fragment.setArguments(args);
        return fragment;

    }




    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogStyle);
        View  view = View.inflate(getContext(), R.layout.dialog_run_locus_detail, null);
        CardRunListBean.AllList.OneList oneList= (CardRunListBean.AllList.OneList) getArguments().getSerializable("oneList");
        TextView cardRunTIme =view.findViewById(R.id.tv_card_run_time);
        TextView cardEndLocation =view.findViewById(R.id.tv_card_run_start_location);
        TextView cardStartLocation =view.findViewById(R.id.tv_card_run_end_location);
        TextView cardRunMileage =view.findViewById(R.id.tv_run_mileage);
        TextView cardTopSpeed =view.findViewById(R.id.tv_run_time);
        TextView cardRunTopSpeed =view.findViewById(R.id.tv_run_top_speed);
        TextView cardRunSpeed =view.findViewById(R.id.tv_run_speed);
        cardRunTIme.setText(FormatUtil.formatDate(oneList.getEnd_time()));
        cardStartLocation.setText(oneList.getStart_name());
        cardEndLocation.setText(oneList.getEnd_name());
        cardRunMileage.setText(oneList.getMileage()+"");
        cardTopSpeed.setText(oneList.getMax_speed()+"");
        cardRunTopSpeed.setText(oneList.getAvg_speed()+"");
        cardRunSpeed.setText(oneList.getMax_speed()+"");
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
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

    }
}
