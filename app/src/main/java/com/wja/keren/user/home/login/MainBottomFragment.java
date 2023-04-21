package com.wja.keren.user.home.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wja.keren.R;
import com.wja.keren.user.home.find.FindDeviceFragment;
import com.wja.keren.user.home.mine.MineFragment;
import com.wja.keren.user.home.home.HomeFragment;
import com.wja.keren.user.home.util.AnimationUtils;

public class MainBottomFragment extends BottomSheetDialogFragment {
    private static final String TAG = MainBottomFragment.class.getName();
    private LinearLayout ivMainTabHome, ivMainTabFind, ivMainTabMine;
    private ImageView ivMainIcon, ivFindIcon, ivMineIcon;
    private TextView tvMainText, tvFindText, tvMineText;
    public HomeFragment homeFragment;
    private FindDeviceFragment findDeviceFragment;
    private MineFragment mineFragment;
    static  ImageView imageView1;

    boolean isOnClickScreen = false;//是否点击屏幕任意处
    public static MainBottomFragment newInstance() {
        MainBottomFragment fragment = new MainBottomFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }
    private static OnClickCloseDialog onDismissCallback;
    public static class InfoService {


        public void setOnClickCloseDialog(OnClickCloseDialog onDismissBack) {
            onDismissCallback = onDismissBack;
        }

    }

    public interface  OnClickCloseDialog{
        void closeDialog(View view);
    }
    View view;



    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogStyle);
         view = View.inflate(getContext(), R.layout.dialog_tab_pop, null);
        ivMainTabHome = view.findViewById(R.id.ll_main_tab);
        ivMainTabFind = view.findViewById(R.id.ll_find_tab);
        ivMainTabMine = view.findViewById(R.id.ll_mine_tab);

        ivMainIcon = view.findViewById(R.id.iv_home_icon);
        ivFindIcon = view.findViewById(R.id.iv_find_icon);
        ivMineIcon = view.findViewById(R.id.iv_mine_icon);

        tvMainText = view.findViewById(R.id.tv_home_text);
        tvFindText = view.findViewById(R.id.tv_find_text);
        tvMineText = view.findViewById(R.id.tv_mine_text);
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
            ivMainTabHome.setOnClickListener(view1 -> {
            ivMainIcon.setImageResource(R.mipmap.main_card);
            ivFindIcon.setImageResource(R.mipmap.main_find_check);//关于这两行：点击按键后按键本身的样子也会发生改变，实现被选中的状态
            ivMineIcon.setImageResource(R.mipmap.main_mine_default);

            tvMainText.setTextColor(getResources().getColor(R.color.color_1FC8A9));
            tvFindText.setTextColor(getResources().getColor(R.color.color_AAAAAA));
            tvMineText.setTextColor(getResources().getColor(R.color.color_AAAAAA));
            homeFragment = new HomeFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.monitor_frame, homeFragment).commit();
            }
        });
           ivMainTabFind.setOnClickListener(view1 -> {

            ivMainIcon.setImageResource(R.mipmap.main_find_check);
            ivFindIcon.setImageResource(R.mipmap.main_card);//关于这两行：点击按键后按键本身的样子也会发生改变，实现被选中的状态
            ivMineIcon.setImageResource(R.mipmap.main_find_check);

            tvFindText.setTextColor(getResources().getColor(R.color.color_1FC8A9));
            tvMainText.setTextColor(getResources().getColor(R.color.color_AAAAAA));
            tvMineText.setTextColor(getResources().getColor(R.color.color_AAAAAA));


            findDeviceFragment = new FindDeviceFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.monitor_frame, findDeviceFragment).commit();
            }

        });
           ivMainTabMine.setOnClickListener(view1 -> {

            ivMainIcon.setImageResource(R.mipmap.main_mine_default);
            ivFindIcon.setImageResource(R.mipmap.main_mine_default);//关于这两行：点击按键后按键本身的样子也会发生改变，实现被选中的状态
            ivMineIcon.setImageResource(R.mipmap.main_card);

            tvFindText.setTextColor(getResources().getColor(R.color.color_1FC8A9));
            tvMainText.setTextColor(getResources().getColor(R.color.color_AAAAAA));
            tvFindText.setTextColor(getResources().getColor(R.color.color_AAAAAA));

            mineFragment = new MineFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.monitor_frame, mineFragment).commit();
            }
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
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissCallback != null) {
            onDismissCallback.closeDialog(view);
        }

    }
}
