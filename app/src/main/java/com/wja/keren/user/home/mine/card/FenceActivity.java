package com.wja.keren.user.home.mine.card;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
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
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.CardConfigBean;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.view.SwitchButton;
import com.wja.keren.user.home.view.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FenceActivity extends BaseActivity implements LocationSource, AMapLocationListener {
    @BindView(R.id.mapView)
    MapView mMapView;

    @BindView(R.id.tv_fence_city_name)
    TextView tvCityName;
    @BindView(R.id.tv_fence_city_name_code)
    TextView tvCityNameCode;
    @BindView(R.id.iv_fence_set_radius)
    TextView tvSetRadius;


    @BindView(R.id.iv_delete_right)
    ImageView ivDis;

    @BindView(R.id.sw_card_senseless_start)
    SwitchButton sw_card_senseless_start;

    @BindView(R.id.ll_bottom_tab)
    LinearLayout linearLayout;
    @BindView(R.id.wheelview1)
    WheelView wheelView; // 滚动选择器

    @BindView(R.id.sw_card_msg_notify)
    SwitchButton msgNotify;

    @BindView(R.id.tv_card_name)
    TextView tvSetValue;

    @BindView(R.id.btn_now_open_ble)
    Button btn_now_open_ble;
    AMap aMap = null;
    LocationSource.OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    private List<String> lowList = new ArrayList<>(); // 低电量设置（%）
    private BitmapDescriptor carBitmap =
            BitmapDescriptorFactory.fromResource(R.mipmap.card_fence_center);
    private Marker mMarker = null;
    private BitmapDescriptor userBitmapMarker =
            BitmapDescriptorFactory.fromResource(R.mipmap.card_fence_center);


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_electronic_fence;
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
        setToolbarTitle(R.string.mine_fence_title);
        setRightText(R.string.mine_user_info_save);
        getConfigQuest();
        wheelView.setCyclic(false); //设置循环滚动
        wheelView.setDividerColor(getColor(R.color.color_1FC8A9));
        wheelView.setTextColorOut(getColor(R.color.editHintColor));
        wheelView.setTextColorCenter(getColor(R.color.color_1FC8A9));
        sw_card_senseless_start.setOnCheckedChangeListener((view, isChecked) -> {
            setAiConfig1(isChecked, Config.USER_ID, 1);

        });

        msgNotify.setOnCheckedChangeListener((view, isChecked) -> {

            setAiConfig1(isChecked, Config.USER_ID, 2);

        });

        tvSetRadius.setOnClickListener(view -> {
            linearLayout.setVisibility(View.VISIBLE);
            setWheelView(wheelView);
        });

        ivDis.setOnClickListener(view -> {
            linearLayout.setVisibility(View.INVISIBLE);

        });

        btn_now_open_ble.setOnClickListener(view -> {
            linearLayout.setVisibility(View.INVISIBLE);

        });
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        // aMap.setMyLocationStyle(carBitmap);
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
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
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
        initLowBatteryData(99);
    }


    @Override
    public void onRight(View view) {
        super.onRight(view);
        setAiConfig(1, 2, 100);
    }

    @SuppressLint("CheckResult")
    private void getConfigQuest() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("index", 0);
        hashMap.put("size", 10);
        hashMap.put("ebike_id", Config.DEVICE_ID);
        HtlRetrofit.getInstance().getService(2).getAiConfig(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                        CardConfigBean cardBean = JSONObject.parseObject(String.valueOf(cardInfo), CardConfigBean.class);
                        if (cardBean.getMsg().equals("ok") || cardBean.getCode().equals("200")) {
                            tvCityName.setText(cardBean.getData().getEbike_set().getFence_center().getLat() + "");
                            tvCityNameCode.setText(cardBean.getData().getEbike_set().getFence_center().getLng() + "");
                            tvSetRadius.setText(cardBean.getData().getEbike_set().getFence_radius() + "");
                            sw_card_senseless_start.setChecked(cardBean.getData().getEbike_set().getElectronic_fence() == 0 ? true : false);
                            msgNotify.setChecked(cardBean.getData().getMsg_set().getHandrail()== 0 ? true : false);
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void setAiConfig(int fenceX, int fenceY, int fence_radius) {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> hashMap1 = new HashMap<>();
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap.put("id", Config.DEVICE_ID);
        hashMap1.put("x", fenceX);
        hashMap1.put("y", fenceY);
        hashMap.put("fence_center", hashMap1);
        hashMap.put("fence_radius", fence_radius);
        hashMap2.put("ebike_set", hashMap);

        System.out.println("电子围栏入参数 == " + hashMap2);
        HtlRetrofit.getInstance().getService(2).setAiConfig(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void setAiConfig1(boolean isCheck, int userId, int flag) {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("id", userId);
        if (flag == 1) {
            hashMap.put("electronic_fence", isCheck ? 0 : 1);
            hashMap1.put("ebike_set", hashMap);
        } else if (flag == 2) {
            hashMap.put("handrail", isCheck ? 0 : 1);
            hashMap1.put("msg_set", hashMap);
        }
        System.out.println("电子围栏入参数 == " + hashMap1);
        HtlRetrofit.getInstance().getService(2).setAiConfig(
                        hashMap1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {

                    }
                });
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
    private void setWheelView(WheelView wheelView) {
        wheelView.setAdapter(new ArrayWheelAdapter(lowList));
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
               String bleName = lowList.get(index);
                tvSetRadius.setText(bleName);
                if (null != bleName) {
                    tvSetValue.setText(getString(R.string.mine_radius_settings) + ": " + bleName);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
        setWheelView(wheelView);
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
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
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                tvCityName.setText(aMapLocation.getCity() + aMapLocation.getDistrict());
                ;//城市信息
                tvCityNameCode.setText(aMapLocation.getStreet() + aMapLocation.getStreetNum() + aMapLocation.getFloor());//城区信息

                aMapLocation.getFloor();//获取当前室内定位的楼层
                aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
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
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
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
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null) {
            mMapView.onDestroy();
        }

    }

}
