package com.wja.keren.user.home.lifecycle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.wja.keren.DemoApplication;
import com.wja.keren.user.home.base.BaseActivity;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Administrator
 */
public class HomeLifecycle implements LifecycleObserver {
    private static final String TAG = "HomeLifecycle";
    private BaseActivity mHomeActivity;

   // private MainHandler mMainHandler;
    public static final  int MSG_SHOW_TIPS = 0x01;
    private static final int DELAY_SCREEN_TIME = 5 * 60 * 1000;
    private static final int DELAY_SCREEN_LONG_TIME = 8 * 60 * 1000;
    private CompositeDisposable disposable;

    private boolean isRegLocation;

    public HomeLifecycle(BaseActivity homeActivity) {
        mHomeActivity = homeActivity;
      //  mMainHandler = new MainHandler();
        disposable = new CompositeDisposable();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Log.d(TAG, "onCreate: ");

        Context context = DemoApplication.getApplication();

    }



//    public void startTipsTimer() {
//        mMainHandler.postDelayed(tipsShowRunable, DELAY_SCREEN_TIME);
//    }

//    public void endTipsTimer() {
//        mMainHandler.removeCallbacks(tipsShowRunable);
//    }
//
//    public void resetTipsTimer(boolean isLong) {
//        mMainHandler.removeCallbacks(tipsShowRunable);
//        mMainHandler.postDelayed(tipsShowRunable, isLong ? DELAY_SCREEN_LONG_TIME : DELAY_SCREEN_TIME);
//    }

//    private Runnable tipsShowRunable = new Runnable() {
//        @Override
//        public void run() {
//            mMainHandler.obtainMessage(MSG_SHOW_TIPS).sendToTarget();
//        }
//    };


    @SuppressLint("HandlerLeak")
//    public class MainHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == MSG_SHOW_TIPS) {
//                List<String> screenUrls = MyNewsManager.getInstance().getNewsScreenUrls();
//                if (SerialService.getInstance().isCutting() || Global.isLoadOrUpdate || screenUrls == null) {
//                    resetTipsTimer(true);
//                } else {
//                    showScreenSaver(mHomeActivity);
//                }
//            }
//        }
//    }

//    private void showScreenSaver(Activity activity) {
//        if (activity != null) {
//            activity.startActivity(new Intent(activity, ScreenSaverActivity.class));
//            activity.overridePendingTransition(R.anim.fade_in, 0);
//        }
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
       // startTipsTimer();
        //tartLocation();
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        //endTipsTimer();
   //     stopLocation();
    }

//    private void startLocation() {
//        if (locationService != null) {
//            locationService.registerListener(mListener);
//            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//            locationService.start();
//            isRegLocation = true;
//        }
//    }

//    private void stopLocation() {
//        if (locationService != null && isRegLocation) {
//            //注销掉监听
//            locationService.unregisterListener(mListener);
//            locationService.stop(); //停止定位服务
//            isRegLocation = false;
//        }
//    }

//    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
//
//        /**
//         * 定位请求回调函数
//         * @param location 定位结果
//         */
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//                // 纬度
//                double latitude = location.getLatitude();
//                // 经度
//                double longitude = location.getLongitude();
//                // 定位类型
//                double locType = location.getLocType();
//                if (latitude != 4.9E-324) {
//                    Global.I_DEVICE_GPS = longitude + "," + latitude;
//                    Log.d(TAG, "onReceiveLocation:  latitude : " + latitude + "longitude: " + longitude);
//                }
//                if (Global.isBootParamsOver) {
//                    stopLocation();
//                }
//            }
//        }
//
//        @Override
//        public void onConnectHotSpotMessage(String s, int i) {
//            super.onConnectHotSpotMessage(s, i);
//        }
//
//        /**
//         * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
//         * @param locType 当前定位类型
//         * @param diagnosticType 诊断类型（1~9）
//         * @param diagnosticMessage 具体的诊断信息释义
//         */
//        @Override
//        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
//            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage);
//        }
//    };


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
//        if (mMainHandler != null) {
//            mMainHandler.removeCallbacksAndMessages(null);
//            mMainHandler = null;
//        }
    }
}