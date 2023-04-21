package com.wja.keren.user.home.mine.card;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

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
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.CardLocationBean;
import com.wja.keren.user.home.main.MainPresenter;
import com.wja.keren.user.home.mine.mvp.CardLocation;
import com.wja.keren.user.home.mine.mvp.CardLocationPresenter;
import com.wja.keren.user.home.util.CheckLocationPermissions;
import com.wja.keren.user.home.util.MapViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class CardLocationActivity extends BaseActivity<CardLocation.Presenter> implements CardLocation.View, LocationSource, AMapLocationListener {

    @BindView(R.id.mMapView)
    MapView mMapView ;

    @BindView(R.id.navigationIv)
    ImageView navigationIv ;

    @BindView(R.id.navigationLayout)
    LinearLayout navigationLayout ;

    @BindView(R.id.locationLayout)
    LinearLayout locationLayout ;

    @BindView(R.id.navigationCancleTv)
    ImageView navigationCancleTv ;

    AMap aMap = null;
    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;

    boolean isFirstLoc = true;
    private BitmapDescriptor carBitmap=
            BitmapDescriptorFactory.fromResource(R.mipmap.card_fence_center);
    private Marker mMarker = null;
    private BitmapDescriptor userBitmapMarker=
            BitmapDescriptorFactory.fromResource(R.mipmap.card_fence_center);

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_map;
    }
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
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
        new CheckLocationPermissions(this,getApplicationContext()).checkPermissions();
        presenter = new CardLocationPresenter(this);
        presenter.attachView(this);
        //setRightIcon(R.mipmap.card_share_add);
       // presenter.queryLocation(Config.DEVICE_ID,"Location");
        setRightText("刷新");
        setToolbarTitle(R.string.card_navigation_location);
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.strokeWidth(15);
        myLocationStyle.myLocationIcon(userBitmapMarker);
        myLocationStyle.strokeColor(getColor(R.color.color_1FC8A9));//设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.radiusFillColor(getColor(R.color.color_1FC8A9));//设置定位蓝点精度圆圈的填充颜色的方法。

        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.MAP_TYPE_SATELLITE);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
        myLocationStyle.showMyLocation(true);//是否显示定位蓝点
        // MyLocationStyle myLocationIcon(BitmapDescriptor myLocationIcon);//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
        //MyLocationStyle anchor(float u, float v);//设置定位蓝点图标的锚点方法。

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

    }


    @Override
    public void onRight(View view) {
        super.onRight(view);
        presenter.queryLocation(Config.DEVICE_ID,"Location");

    }

    /**
     * 定位初始化
     */

    @OnClick({R.id.navigationIv,R.id.navigationCancleTv,R.id.gaodeTv,R.id.baiduTv})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.navigationIv:
                navigationLayout.setVisibility(View.VISIBLE);
                locationLayout.setVisibility(View.INVISIBLE);
                break;
            case R.id.navigationCancleTv:
                navigationLayout.setVisibility(View.INVISIBLE);
                locationLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.gaodeTv:
                MapViewModel.goThirdMap(this,1,"",0.5,0.6);

                break;
            case R.id.baiduTv:
                MapViewModel.goThirdMap(this,0,"",0.5,0.6);
                break;
            default:
                break;
        }
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
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            try {
                mlocationClient = new AMapLocationClient(getApplicationContext());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();

            //设置定位回调监听
              mlocationClient.setLocationListener(this::onLocationChanged);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
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
    public void showCardLocationInfo(CardLocationBean.CardLocation location) {

    }
}
