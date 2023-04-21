package com.wja.keren.user.home.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.wja.keren.user.home.view.ToastUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URISyntaxException;

import kotlin.jvm.internal.Intrinsics;

public class MapViewModel {

    public static void goThirdMap(@NotNull Activity context, int type, @NotNull String address, double destinationLatitude, double destinationLongtitude) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(address, "address");
        switch (type) {
            case 0:
                if (SysUtils.INSTANCE.isInstallApk("com.baidu.BaiduMap")) {
                    try {
                        Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:" + destinationLatitude + "," + destinationLongtitude + "|name:" + "" + "&mode=driving&" + "region=" + "&src=#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                        context.startActivity(intent);
                    } catch (URISyntaxException var12) {
                     //   LogUtils.e(var12.getMessage(), new Object[0]);
                    }
                } else {
                    ToastUtils.ToastMessage(context,"您尚未安装百度地图");

                }
                break;
            case 1:
                if (SysUtils.INSTANCE.isInstallApk("com.autonavi.minimap")) {
                    DPoint gdLatLng = bd2gcj(destinationLatitude, destinationLongtitude, (Context)context);

                    Uri var10000 = Uri.parse("amapuri://route/plan/?dlat=" + (gdLatLng != null ? gdLatLng.getLatitude() : null) + "&dlon=" + (gdLatLng != null ? gdLatLng.getLongitude() : null) + "&dev=0&t=2");
                    Intrinsics.checkNotNullExpressionValue(var10000, "Uri.parse(\"amapuri://rou…g?.longitude}&dev=0&t=2\")");
                    Uri uri = var10000;
                    Intent intent = new Intent("android.intent.action.VIEW", uri);
                    intent.addCategory("android.intent.category.DEFAULT");
                    context.startActivity(intent);
                } else {
                    ToastUtils.ToastMessage(context,"您尚未安装高德地图");

                }
        }

    }

    @Nullable
    public static   DPoint bd2gcj(double latitude, double longitude, @NotNull Context cotext) {
        Intrinsics.checkNotNullParameter(cotext, "cotext");
        DPoint sourceLatLng = new DPoint(latitude, longitude);
        CoordinateConverter converter = new CoordinateConverter(cotext);
        converter.from(CoordinateConverter.CoordType.BAIDU);
        try {
            converter.coord(sourceLatLng);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DPoint desLatLng = null;
        try {
            desLatLng = converter.convert();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return desLatLng;
    }
}
