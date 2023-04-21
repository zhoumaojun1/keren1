package com.wja.keren.user.home.mine.card;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseFragment;
import com.wja.keren.user.home.bean.CardRunBean;
import com.wja.keren.user.home.bean.CardRunListBean;
import com.wja.keren.user.home.mine.CardRunPresenter;
import com.wja.keren.user.home.mine.CardRunSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class RunAllFragment extends BaseFragment<CardRunSet.Presenter> implements CardRunSet.View , OnChartValueSelectedListener {

    @BindView(R.id.tv_all_mileage)
    TextView tvAllMileage;

    @BindView(R.id.tv_long_mileage)
    TextView tvLongMileage;

    @BindView(R.id.tv_accompany_time)
    TextView tvAccompanyTime;

    @BindView(R.id.tv_past_15_mileage)
    TextView tvPastTime;

    @BindView(R.id.tv_past_15_top_speed)
    TextView tvPastTopSpeed;


    @BindView(R.id.tv_past_15_average_speed)
    TextView tvPastAverageSpeed;

    @BindView(R.id.tv_past_15_run_number)
    TextView tvPastRunNumber;
    @BindView(R.id.mBarChart)
    BarChart mBarChart;
    /**
     * 一天当中的秒数
     */
    private static final int DAY_SECONDS = 24 * 3600;
    public static RunAllFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        RunAllFragment fragment = new RunAllFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public CardRunSet.Presenter createPresenter() {
        return new CardRunPresenter(getContext());
    }

    @Override
    protected void init() {
        //开始结束时间
        long beginTime = 0;
        long endTime = 0;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH) + 1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int startDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        beginTime = (calendar.getTimeInMillis() / 1000L);
        endTime = beginTime + DAY_SECONDS - 1;
  // initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.runRouteAllData(1680346807 ,1681124408, Config.DEVICE_ID);
    }

    @SuppressLint("CheckResult")
    private  void initData(){
        //条形图
        //设置表格上的点，被点击的时候，的回调函数
        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        // 如果60多个条目显示在图表,drawn没有值
        mBarChart.setMaxVisibleValueCount(60);
        // 扩展现在只能分别在x轴和y轴
        mBarChart.setPinchZoom(false);
        //是否显示表格颜色
        mBarChart.setDrawGridBackground(false);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        // 只有1天的时间间隔
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setAxisMaximum(50f);
        xAxis.setAxisMinimum(0f);
       // xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        //这个替换setStartAtZero(true)
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(50f);
        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(50f);
    }

    //设置数据
    private void setData(ArrayList yVals1) {
        float start = 1f;
        BarDataSet set1;
        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "2017年工资涨幅");
            //设置有四种颜色
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            //设置数据
            mBarChart.setData(data);
        }
    }

    @Override
    public void showCardList(List<CardRunListBean.AllList.OneList> userHead) {

    }

    @Override
    public void showRouteAllData(CardRunBean cardRunBean) {
        CardRunBean.CardRun cardRun =cardRunBean.getCardRun();
        if (cardRun == null) {
            return;
        }

        tvAllMileage.setText(cardRun.getAccumulated_mileage());
        tvLongMileage.setText(cardRun.getCycling_count());
        tvAccompanyTime.setText(cardRun.getCycling_count());
        tvPastTime.setText(cardRun.getAccumulated_mileage());
        tvPastTopSpeed.setText(cardRun.getMax_speed());
        tvPastAverageSpeed.setText(cardRun.getAvg_speed());
        tvPastRunNumber.setText(cardRun.getCycling_count());
    }

    @Override
    public void showError(int resId) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
