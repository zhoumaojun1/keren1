package com.wja.keren.user.home.mine.card;

import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.ServiceSettings;
import com.wja.keren.DemoApplication;
import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.CardRunListBean;
import com.wja.keren.user.home.bean.LoginInfoBean;
import com.wja.keren.user.home.mine.card.dialog.RunClockFragment;
import com.wja.keren.user.home.mine.card.dialog.RunTrackDetailsFragment;

import butterknife.BindView;

public class TrackDetailsActivity extends BaseActivity implements LocationSource, AMapLocationListener {
    @BindView(R.id.mapView)
    MapView mMapView ;
    AMap aMap = null;
    LocationSource.OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;


    private BitmapDescriptor carBitmap=
            BitmapDescriptorFactory.fromResource(R.mipmap.card_fence_center);
    private Marker mMarker = null;
    private BitmapDescriptor userBitmapMarker=
            BitmapDescriptorFactory.fromResource(R.mipmap.card_fence_center);

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_run_track_details;
    }

    CardRunListBean.AllList.OneList oneList;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
      //  setContentView(R.layout.activity_run_track_details);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("oneList") != null) {
               oneList= ( CardRunListBean.AllList.OneList) bundle.getSerializable("oneList");
            }
        }
//        mMapView = findViewById(R.id.mapView);
        showBottomCardTimeSheetDialog(oneList);
        mMapView.onCreate(saveInstanceState);
        init();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
    }
    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_fence_title);
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        myLocationStyle.strokeWidth(15);
        myLocationStyle.myLocationIcon(userBitmapMarker);
        myLocationStyle.strokeColor(getColor(R.color.color_1FC8A9));//设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.radiusFillColor(getColor(R.color.color_1FC8A9));//设置定位蓝点精度圆圈的填充颜色的方法。

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.MAP_TYPE_SATELLITE);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
        myLocationStyle.showMyLocation(true);//是否显示定位蓝点
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
        myLocationStyle.showMyLocation(true);//是否显示定位蓝点
        // MyLocationStyle myLocationIcon(BitmapDescriptor myLocationIcon);//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
      //  MyLocationStyle.anchor(float u, float v);//设置定位蓝点图标的锚点方法。

        myLocationStyle.strokeColor(R.color.color_1FC8A9);//设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.radiusFillColor(R.color.color_1FC8A9);//设置定位蓝点精度圆圈的填充颜色的方法。
        mLocationOption = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mlocationClient) {
            mlocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            // mlocationClient.stopLocation();
            mlocationClient.startLocation();
            mLocationOption.setInterval(2000);
            mLocationOption.setMockEnable(true);
            mLocationOption.setNeedAddress(true);
            mLocationOption.setOnceLocation(true);
            //获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
            mLocationOption.setOnceLocationLatest(true);
//关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);
        }
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        //  aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }
    private void showBottomCardTimeSheetDialog( CardRunListBean.AllList.OneList oneList) {
        RunTrackDetailsFragment fragment = RunTrackDetailsFragment.newInstance(oneList);
        fragment.show(getSupportFragmentManager(), RunTrackDetailsFragment.class.getSimpleName());
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            try {
                mlocationClient = new AMapLocationClient(DemoApplication.getApplication());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null) {
            mMapView.onDestroy();
        }

    }
}
