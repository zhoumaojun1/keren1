package com.wja.keren.user.home.util;

import static com.wja.keren.user.home.main.HomeTabActivity.RC_CAMERA;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.wja.keren.R;

import pub.devrel.easypermissions.EasyPermissions;

public class CheckLocationPermissions {

    private Activity activity;
    private Context mContext;
    public CheckLocationPermissions(Activity activity,Context context) {
        this.mContext = context;
       this.activity =activity;
    }

    public void checkPermissions(){
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION};
        if (EasyPermissions.hasPermissions(mContext, perms)) {//有权限
        } else {
            EasyPermissions.requestPermissions(activity, activity.getString(R.string.permission_location),
                    RC_CAMERA, perms);
        }
    }

}
