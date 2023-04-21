package com.wja.keren.user.home.mine.card.dialog;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.login.MainBottomFragment;
import com.wja.keren.user.home.mine.card.AiConfigActivity;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.util.AnimationUtils;
import com.wja.keren.user.home.view.PublicModel;
import com.wja.keren.user.home.view.PublicScrollView;
import com.wja.keren.user.home.view.ToastUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PublicFragment extends BottomSheetDialogFragment implements PublicScrollView.onSelectListener {
    private static final String TAG = MainBottomFragment.class.getName();

    WheelView wheelView; // 滚动选择器

    private List<String> bleNameList = new ArrayList<>(); // 蓝牙强度
    private List<String> lowList = new ArrayList<>(); // 低电量设置（%）
    private List<String> lockList = new ArrayList<>(); // 离车自动锁定倒计时(S)


    public static PublicFragment newInstance(int value) {
        PublicFragment fragment = new PublicFragment();
        Bundle args = new Bundle();
        args.putInt("value", value);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onSelect(PublicModel pickers) {
        System.out.println("选择：" + pickers.getIndex() + "--银行："
                + pickers.getContent());
    }

    int value;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        value = getArguments().getInt("value", 0);


    }

//    public void onFloat(View view) {
//        NumberPicker picker = new NumberPicker(this);
//        picker.setBodyWidth(120);
//        picker.setOnNumberPickedListener(this);
//        picker.getWheelLayout().setOnNumberSelectedListener(new OnNumberSelectedListener() {
//            @Override
//            public void onNumberSelected(int position, Number item) {
//                picker.getTitleView().setText(picker.getWheelView().formatItem(position));
//            }
//        });
//        picker.setFormatter(new WheelFormatter() {
//            @Override
//            public String formatItem(@NonNull Object item) {
//                DecimalFormat df = new DecimalFormat("0.00");
//                return df.format(item);
//            }
//        });
//        picker.setRange(-10f, 10f, 0.1f);
//        picker.setDefaultValue(5f);
//        picker.getTitleView().setText("温度选择");
//        picker.getLabelView().setText("℃");
//        picker.show();
//    }
//

    /**
     * 初始化电量数据
     */
    private void initData() {
        for (int i = 0; i < 99; i++) {
            if (i == 1) {
                lowList.add("1");
            } else if (i == 2) {
                lowList.add("2");
            } else if (i == 3) {
                lowList.add("3");
            } else if (i == 4) {
                lowList.add("4");
            } else if (i == 5) {
                lowList.add("5");
            } else if (i == 6) {
                lowList.add("6");
            } else if (i == 7) {
                lowList.add("7");
            } else if (i == 8) {
                lowList.add("8");
            } else if (i == 9) {
                lowList.add("9");
            } else if (i == 10) {
                lowList.add("10");
            }
            else if (i == 11) {
                lowList.add("11");
            }
            else if (i == 12) {
                lowList.add("12");
            }
            else if (i == 13) {
                lowList.add("13");
            } else if (i == 14) {
                lowList.add("14");
            }
        }
    }

    /**
     * 初始化离车倒计时数据
     */
    private void initData1() {
        for (int i = 0; i < 20; i++) {
            if (i == 1) {
                lockList.add("10");
            } else if (i == 2) {
                lockList.add("20");
            } else if (i == 3) {
                lockList.add("30");
            } else if (i == 4) {
                lockList.add("40");
            } else if (i == 5) {
                lockList.add("50");
            } else if (i == 6) {
                lockList.add("60");
            } else if (i == 7) {
                lockList.add("70");
            } else if (i == 8) {
                lockList.add("80");
            } else if (i == 9) {
                lockList.add("90");
            } else if (i == 10) {
                lockList.add("100");
            } else if (i == 11) {
                lockList.add("110");
            } else if (i == 12) {
                lockList.add("120");
            } else if (i == 13) {
                lockList.add("150");
            } else if (i == 14) {
                lockList.add("200");
            } else if (i == 15) {
                lockList.add("220");
            } else if (i == 16) {
                lockList.add("230");
            } else if (i == 17) {
                lockList.add("240");
            } else if (i == 18) {
                lockList.add("250");
            } else if (i == 19) {
                lockList.add("300");
            }


        }
    }

    /**
     * 初始化蓝牙数据
     */
    private void initData2() {
        bleNameList.add("远");
        bleNameList.add("中");
        bleNameList.add("近");
    }
    String bleName;
    private void setWheelView(WheelView wheelView, int value) {
        if (value == 3) {
            wheelView.setAdapter(new ArrayWheelAdapter(bleNameList));
        } else if (value == 2) {
            wheelView.setAdapter(new ArrayWheelAdapter(lockList));
        } else if (value == 1) {
            wheelView.setAdapter(new ArrayWheelAdapter(lowList));
        }
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (value == 3) {
                     bleName = bleNameList.get(index);
                     if (null!=bleName){
                         setValue.setText(getString(R.string.mine_low_battery_reminder_settings) + bleName + "%");
                     }

                } else if (value == 2) {
                     bleName =lockList.get(index);
                   // setValue.setText(getString(R.string.mine_exit_countdown_off) + bleName + "%");
                } else if (value == 1) {
                     bleName =lowList.get(index);
                   //  setValue.setText(getString(R.string.mine_ble_strength) + bleName + "%");
                }

            }
        });
    }

    TextView setValue;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogStyle);
        View view = View.inflate(getContext(), R.layout.dialog_public_view, null);
        Button btnConfirm = view.findViewById(R.id.btn_now_open_ble);
        wheelView = view.findViewById(R.id.wheelview1);
        setValue = view.findViewById(R.id.tv_card_name);
        ImageView ivDelete = view.findViewById(R.id.iv_delete_right);
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
        btnConfirm.setOnClickListener(view1 -> {
            dismiss();
            if (bleName != null) {
              //  AiConfigActivity.submit(bleName);
                setAiConfig(value,bleName);
            }
        });
        ivDelete.setOnClickListener(view1 -> {
            dismiss();
        });
        return bottomSheetDialog;
    }
    @SuppressLint("CheckResult")
    private void setAiConfig(int flag,String value) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", Config.DEVICE_ID);
        if (flag == 1 ){
            hashMap.put("power_value",value);
        } else if (flag == 2){
            hashMap.put("leave_countdown",value);
        } else if (flag == 3){
            hashMap.put("open_lock_distance",value);
        }
        HtlRetrofit.getInstance().getService(2).setAiConfig(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        Log.d("setAiConfig","成功");

                    }
                });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (value == 1) {
            initData();
        } else if (value == 2) {

            initData1();
        } else if (value == 3) {
            initData2();
        }
        wheelView.setCyclic(false); //设置循环滚动
        wheelView.setDividerColor(getActivity().getColor(R.color.color_1FC8A9));
        wheelView.setTextColorOut(getActivity().getColor(R.color.color_1FC8A9));
        wheelView.setTextColorCenter(getActivity().getColor(R.color.color_1FC8A9));
        setWheelView(wheelView,value);
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
