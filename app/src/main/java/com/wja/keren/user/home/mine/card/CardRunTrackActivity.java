package com.wja.keren.user.home.mine.card;

import android.os.Bundle;

import com.amap.api.maps2d.MapView;
import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;

import butterknife.BindView;

public class CardRunTrackActivity extends BaseActivity {
    @BindView(R.id.mapView)
    MapView mMapView ;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_run_track_details;
    }


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(saveInstanceState);
    }

    @Override
    protected void init() {

        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_fence_title);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
