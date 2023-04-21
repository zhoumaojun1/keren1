package com.wja.keren.user.home.mine.card;

import static com.wja.keren.user.home.Config.USER_ID;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.CardConfigBean;
import com.wja.keren.user.home.mine.mvp.AiConfig;
import com.wja.keren.user.home.mine.mvp.AiConfigPresenter;
import com.wja.keren.user.home.view.SwitchButton;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AiConfigActivity extends BaseActivity<AiConfig.Presenter> implements AiConfig.View {
    private static final String TAG = AiConfigActivity.class.getName();
    @BindView(R.id.tv_card_low_battery)
    TextView tvLowBattery;
    @BindView(R.id.iv_fence_set_scope)
    TextView tvSetScope;
    @BindView(R.id.iv_card_exit_countdown)
    TextView tvExitCountdown;
    @BindView(R.id.tv_ble_type)
    TextView tvBleType;
    @BindView(R.id.sw_card_senseless_start)
    SwitchButton swSen;
    @BindView(R.id.switch_loop_finger_login)
    SwitchButton swLoop;

    @BindView(R.id.switch_card_error_shake)
    SwitchButton swShake;

    @BindView(R.id.switch_source_break)
    SwitchButton swLock;
    @BindView(R.id.switch_source_break_monitor)
    SwitchButton swMonitor;
    @BindView(R.id.switch_source_guard_alarm)
    SwitchButton swGuard;
    @BindView(R.id.ll_bottom_tab)
    LinearLayout linearLayout;
    @BindView(R.id.wheelview1)
    WheelView wheelView; // 滚动选择器

    @BindView(R.id.tv_card_name)
    TextView tvSetValue;

    @BindView(R.id.btn_now_open_ble)
    Button btn_now_open_ble;

    private List<String> bleNameList = new ArrayList<>(); // 蓝牙强度
    private List<String> lowList = new ArrayList<>(); // 低电量设置（%）
    private List<String> lockList = new ArrayList<>(); // 离车自动锁定倒计时(S)

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_ai_config;
    }
    CardConfigBean.CardConfig    cardConfig1;
    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_ai_config);
        presenter = new AiConfigPresenter(this);
        presenter.attachView(this);
        presenter.getAiConfig();
        wheelView.setCyclic(false); //设置循环滚动
        wheelView.setDividerColor(getColor(R.color.color_1FC8A9));
        wheelView.setTextColorOut(getColor(R.color.editHintColor));
        wheelView.setTextColorCenter(getColor(R.color.color_1FC8A9));

        initExitCountdownData(300);
        initBleTypeData();

            cardConfig1  =new CardConfigBean.CardConfig();
        swSen.setOnCheckedChangeListener((view, isChecked) -> {
       //     presenter.openAiConfig(3,"", isChecked,Config.USER_ID );
        });
        swLoop.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setAiConfig(2, isChecked,Config.USER_ID );
        });
        swShake.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setAiConfig(3, isChecked, Config.USER_ID);
        });
        swLock.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setAiConfig(4, isChecked, Config.USER_ID);
        });
        swMonitor.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.openAiConfig(2, "",isChecked, Config.USER_ID);

        });
        swGuard.setOnCheckedChangeListener((view, isChecked) -> {
            presenter.setAiConfig(6, isChecked, Config.USER_ID);
        });
        initLowBatteryData(99);
    }


    /**
     * 初始化电量数据
     */
    private void initLowBatteryData(int value) {
        String tmpStr = String.valueOf(value);
        for (int i = 0; i < value; i++) {

            tmpStr = "" + i;
            lowList.add(tmpStr);
        }

    }

    /**
     * 初始化离车倒计时数据
     */
    private void initExitCountdownData(int value) {
        String tmpStr = String.valueOf(value);

        for (int i = 0; i < value; i++) {
            tmpStr = "" + i;
            lockList.add(tmpStr);
        }


    }

    /**
     * 初始化蓝牙数据
     */
    private void initBleTypeData() {
        Log.d(TAG, "初始化蓝牙数据");
        bleNameList.add("强");
        bleNameList.add("中");
        bleNameList.add("弱");


    }

    @Override
    public void onResume() {
        super.onResume();

        setWheelView(wheelView, 2);
        setWheelView(wheelView, 1);
        setWheelView(wheelView, 3);
    }

    void setTag(int tag) {
        if (tag == 3) {
            //  lockList.clear();
            //lowList.clear();
            setWheelView(wheelView, 3);
        } else if (tag == 2) {
            //bleNameList.clear();
            //lockList.clear();
            setWheelView(wheelView, 2);
        } else if (tag == 1) {
            // bleNameList.clear();
            // lowList.clear();
            setWheelView(wheelView, 1);
        }

    }

    String bleName;

    private void setWheelView(WheelView wheelView, int value) {
        Log.e(TAG, "setWheelView" + wheelView);
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
                    tvBleType.setText(bleName);
                    if (null != bleName) {
                        Log.d(TAG, getString(R.string.mine_low_battery_reminder_settings) + bleName);
                        tvSetValue.setText(getString(R.string.mine_low_battery_reminder_settings) + ": " + bleName);
                    }
                } else if (value == 2) {
                    bleName = lockList.get(index);
                    tvExitCountdown.setText(bleName);
                    tvSetValue.setText(getString(R.string.mine_exit_countdown_off) + bleName + "%");
                    Log.d(TAG, getString(R.string.mine_exit_countdown_off) + bleName);
                } else if (value == 1) {
                    bleName = lowList.get(index);
                    tvLowBattery.setText(bleName);
                    tvSetValue.setText(getString(R.string.mine_ble_strength) + bleName + "%");

                    Log.d(TAG, getString(R.string.mine_ble_strength) + bleName);
                }

            }
        });
    }

    int click = 0;

    @OnClick({R.id.iv_fence_set_scope, R.id.tv_card_low_battery
            , R.id.iv_card_exit_countdown, R.id.tv_ble_type, R.id.iv_delete_right, R.id.btn_now_open_ble})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.iv_fence_set_scope:
                IntentUtil.get().goActivity(this, FenceActivity.class);
                break;

            case R.id.iv_card_exit_countdown:
                tvSetValue.setText(R.string.mine_exit_countdown_off);
                click = 1;
                setTag(2);
                linearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_card_low_battery:
                click = 2;
                tvSetValue.setText(R.string.mine_low_battery_reminder_settings);
                setTag(1);
                linearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_ble_type:
                click = 3;
                tvSetValue.setText(R.string.mine_ble_strength);
                linearLayout.setVisibility(View.VISIBLE);
                setTag(3);
                break;
            case R.id.iv_delete_right:
                linearLayout.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_now_open_ble:
                linearLayout.setVisibility(View.INVISIBLE);
                if (click == 1) {
                    tvExitCountdown.setText(bleName);
                    presenter.setAiConfig1(click, bleName, Config.USER_ID);
                } else if (click == 2) {
                    tvLowBattery.setText(bleName);
                    presenter.setAiConfig1(click, bleName, Config.USER_ID);
                } else if (click == 3) {
                    presenter.openAiConfig(3, bleName,true, Config.USER_ID);
                    tvBleType.setText(bleName);
                }

                Log.d(TAG,"用户id==" +USER_ID+"");
                break;
            default:
                break;
        }
    }


    @Override
    public void updateAiConfig() {

    }

    @Override
    public void showAiConfigInfo(CardConfigBean cardConfig) {
        CardConfigBean    cardConfig1  = cardConfig;
        CardConfigBean.CardConfig config= cardConfig1.getData();
        config.setId(config.getId());
        Config.USER_ID = config.getId();
        Log.d(TAG, "showAiConfigInfo" + config.getId());
        tvExitCountdown .setText(config.getEbike_set().getPower_value() + "");
        tvSetScope.setText(config.getEbike_set().getFence_radius() + "");
        tvLowBattery.setText(config.getEbike_set().getLeave_countdown() + "");
        tvBleType.setText(config.getEbike_set().getOpen_lock_distance() + "");

        swSen.setChecked(config.getEbike_set().getOpen_lock() == 0 ? true : false);
        swLoop.setChecked(config.getEbike_set().getHitchs_up() == 0 ? true : false);
        swShake.setChecked(config.getEbike_set().getAlarm() == 0 ? true : false);
        swLock.setChecked(config.getFly_car() == 0 ? true : false);
        swMonitor.setChecked(config.getHill_assist() == 0 ?true  : false);
        Log.d(TAG, "上坡辅助" + config.getHill_assist());
        if (config.getEbike_set().getOpen_lock_distance()==3){
            tvBleType.setText("强");
        }else if (config.getEbike_set().getOpen_lock_distance()==2){
            tvBleType.setText("中");
        }else {
            tvBleType.setText("弱");
        }

    }
}
