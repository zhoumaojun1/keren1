package com.wja.keren.zxing.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

/**
 * Intent跳转封装
 * Created by Administrator on 2018/1/16 0016.
 */
public class IntentUtil {
    public static final String OPEN_ACTIVITY_KEY = "open_activity";//intent跳转传递传输key
    private static IntentUtil manager;
    private static Intent intent;

    private IntentUtil() {

    }

    public static IntentUtil get() {
        if (manager == null) {
            synchronized (IntentUtil.class) {
                if (manager == null) {
                    manager = new IntentUtil();
                }
            }
        }
        intent = new Intent();
        return manager;
    }

    /**
     * 获取上一个界面传递过来的参数
     *
     * @param activity this
     * @param <T>      泛型
     * @return
     */
    public <T> T getParcelableExtra(Activity activity) {
        Parcelable parcelable = activity.getIntent().getParcelableExtra(OPEN_ACTIVITY_KEY);
        activity = null;
        return (T) parcelable;
    }

    /**
     * 启动一个Activity
     *
     * @param activity
     * @param _class
     */
    public void goActivity(Context activity, Class<? extends Activity> _class) {
        intent.setClass(activity, _class);
        activity.startActivity(intent);
        activity = null;
    }
    public void goActivityResult(Context activity, Class<? extends Activity> _class, Intent bundle) {
        intent.setClass(activity, _class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity = null;
    }
    public void goActivityResult(Context activity, Class<? extends Activity> _class, String bundle) {
        intent.setClass(activity, _class);
        intent.putExtra("bundle",bundle);
        activity.startActivity(intent);
        activity = null;
    }
    /**
     * 启动activity后kill前一个
     *
     * @param activity
     * @param _class
     */
    public void goActivityKill(Context activity, Class<? extends Activity> _class) {
        intent.setClass(activity, _class);
        activity.startActivity(intent);
        ((Activity) activity).finish();
        activity = null;
    }

    /**
     * 回调跳转
     *
     * @param activity
     * @param _class
     * @param requestCode
     */
    public void goActivityResult(Activity activity, Class<? extends Activity> _class, int requestCode) {
        intent.setClass(activity, _class);
        activity.startActivityForResult(intent, requestCode);
        activity = null;
    }

    /**
     * 回调跳转并finish当前页面
     *
     * @param activity
     * @param _class
     * @param requestCode
     */
    public void goActivityResultKill(Activity activity, Class<? extends Activity> _class, int requestCode) {
        intent.setClass(activity, _class);
        activity.startActivityForResult(intent, requestCode);
        ((Activity) activity).finish();
        activity = null;
    }

    /**
     * 传递一个序列化实体类
     *
     * @param activity
     * @param _class
     * @param parcelable
     */
    public void goActivity(Context activity, Class<? extends Activity> _class, Parcelable parcelable) {
        intent.setClass(activity, _class);
        putParcelable(parcelable);
        activity.startActivity(intent);
        activity = null;
    }

    /**
     * 启动一个Activity
     *
     * @param activity
     * @param _class
     * @param flags
     * @param parcelable 传递的实体类
     */
    public void goActivity(Context activity, Class<? extends Activity> _class, int flags, Parcelable parcelable) {
        intent.setClass(activity, _class);
        setFlags(flags);
        putParcelable(parcelable);
        activity.startActivity(intent);
        activity = null;
    }

    public void goActivityKill(Context activity, Class<? extends Activity> _class, Parcelable parcelable) {
        intent.setClass(activity, _class);
        putParcelable(parcelable);
        activity.startActivity(intent);
        ((Activity) activity).finish();
        activity = null;
    }

    public void goActivityKill(Context activity, Class<? extends Activity> _class, int flags, Parcelable parcelable) {
        intent.setClass(activity, _class);
        setFlags(flags);
        putParcelable(parcelable);
        activity.startActivity(intent);
        ((Activity) activity).finish();
        activity = null;
    }

    public void goActivityResult(Activity activity, Class<? extends Activity> _class, Parcelable parcelable, int requestCode) {
        intent.setClass(activity, _class);
        putParcelable(parcelable);
        activity.startActivityForResult(intent, requestCode);
        activity = null;
    }

    public void goActivityResult(Activity activity, Class<? extends Activity> _class, int flags, Parcelable parcelable, int requestCode) {
        intent.setClass(activity, _class);
        putParcelable(parcelable);
        setFlags(flags);
        activity.startActivityForResult(intent, requestCode);
        activity = null;
    }

    public void goActivityResultKill(Activity activity, Class<? extends Activity> _class, Parcelable parcelable, int requestCode) {
        intent.setClass(activity, _class);
        putParcelable(parcelable);
        activity.startActivityForResult(intent, requestCode);
        activity.finish();
        activity = null;
    }

    public void goActivityResultKill(Activity activity, Class<? extends Activity> _class, int flags, Parcelable parcelable, int requestCode) {
        intent.setClass(activity, _class);
        putParcelable(parcelable);
        setFlags(flags);
        activity.startActivityForResult(intent, requestCode);
        activity.finish();
        activity = null;
    }

    private void setFlags(int flags) {
        if (flags < 0) return;
        intent.setFlags(flags);
    }

    private void putParcelable(Parcelable parcelable) {
        if (parcelable == null) return;
        intent.putExtra(OPEN_ACTIVITY_KEY, parcelable);
    }
}
