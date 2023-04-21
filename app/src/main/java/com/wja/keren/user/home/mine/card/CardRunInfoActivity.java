package com.wja.keren.user.home.mine.card;

import android.widget.TextView;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;

import butterknife.BindView;

public class CardRunInfoActivity extends BaseActivity {
    @BindView(R.id.tv_battery_volume_value)
    TextView tvVolume;

    @BindView(R.id.tv_battery_range_value)
    TextView tvRange;

    @BindView(R.id.tv_battery_level_value)
    TextView tvLevel;

    @BindView(R.id.tv_battery_health_value)
    TextView tvHealth;

    @BindView(R.id.tv_battery_charging_hour_value)
    TextView tvChargingHour;

    @BindView(R.id.tv_battery_charging_minute_value)
    TextView tvChargingMinute;

    @BindView(R.id.tv_battery_tmp_value)
    TextView tvTmp;

    @BindView(R.id.tv_battery_voltage_value)
    TextView tvVoltage;

    @BindView(R.id.tv_battery_charging_value)
    TextView tvCharging;

    @BindView(R.id.tv_card_gps_status)
    TextView tvGpsStatus;

    @BindView(R.id.tv_card_ble_connect_status)
    TextView tvBleConnectStatus;

    @BindView(R.id.tv_card_4g_connect_status)
    TextView tv4gConnectStatus;

    @BindView(R.id.tv_card_alarms_number)
    TextView tvAlarmsNum;

    @BindView(R.id.tv_card_failures_number)
    TextView tvFailuresNum;

    @BindView(R.id.tv_card_shake_number)
    TextView tvShakeNum;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_card_run_info;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_card_running_info);
        initCardParameter();

    }
    private void initCardParameter(){
        tvVolume.setText("72");
        tvRange.setText("98");
        tvLevel.setText("98");
        tvHealth.setText("100");
        tvChargingHour.setText("9");
        tvChargingMinute.setText("59");
        tvTmp.setText("23");
        tvVoltage.setText("72");
        tvVoltage.setText("72");
        tvCharging.setText("480");
        tvGpsStatus.setText("正常");
        tvBleConnectStatus.setText("异常");
        tv4gConnectStatus.setText("异常");
        tvAlarmsNum.setText("12");
        tvFailuresNum.setText("5");
        tvShakeNum.setText("0");
    }
}
