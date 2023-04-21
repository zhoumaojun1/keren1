package com.wja.keren.user.home.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.LocaleList;
import android.os.Looper;
import android.os.SystemClock;

import com.wja.keren.DemoApplication;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import kotlin.jvm.internal.Intrinsics;

public class SysUtils {
    private static final int MIN_CLICK_DELAY_TIME = 300;
    private static long lastClickTime;
    @NotNull
    public static final SysUtils INSTANCE;
    public final boolean isDoubleClick() {
        long currentTime = SystemClock.uptimeMillis();
        if (currentTime - lastClickTime > (long)300) {
            lastClickTime = currentTime;
            return true;
        } else {
            return false;
        }
    }

    public final String getSystemLanguage() {
        Locale locale = null;
        Locale var10000;
        if (Build.VERSION.SDK_INT >= 24) {
            var10000 = LocaleList.getDefault().get(0);
            Intrinsics.checkNotNullExpressionValue(var10000, "LocaleList.getDefault()[0]");
        } else {
            var10000 = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(var10000, "Locale.getDefault()");
        }

        locale = var10000;
        return locale.getLanguage() + "-" + locale.getCountry();
    }
    public final boolean isMainThread() {
        return Intrinsics.areEqual(Looper.getMainLooper(), Looper.myLooper());
    }
    public final boolean checkNetConttent(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object var10000 = context.getSystemService("connectivity");
        if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.net.ConnectivityManager");
        } else {
            ConnectivityManager cm = (ConnectivityManager)var10000;
            if (cm != null) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null && info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }

            return false;
        }
    }
//    public  NetWorkType getNetworkType(@NotNull Context context) {
//        Intrinsics.checkNotNullParameter(context, "context");
//        NetWorkType netWorkType = SysUtils.NetWorkType.NETWORK_UNKNOWN;
//        Object var10000 = context.getSystemService("connectivity");
//        if (var10000 == null) {
//            throw new NullPointerException("null cannot be cast to non-null type android.net.ConnectivityManager");
//        } else {
//            NetworkInfo networkInfo = ((ConnectivityManager)var10000).getActiveNetworkInfo();
//            if (networkInfo != null && networkInfo.isConnected()) {
//                if (networkInfo.getType() == 1) {
//                    netWorkType = SysUtils.NetWorkType.WIFI;
//                } else if (networkInfo.getType() == 0) {
//                    int networkTypeNum = networkInfo.getSubtype();
//
//                    try {
//                        Class threadClazz = Class.forName("android.telephony.TelephonyManager");
//                        Method method = threadClazz.getMethod("getNetworkClass", Integer.TYPE);
//                        var10000 = method.invoke((Object)null, networkTypeNum);
//                        if (var10000 == null) {
//                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
//                        }
//
//                        int invoke = (Integer)var10000;
//                        NetWorkType[] var10 = SysUtils.NetWorkType.values();
//                        int var11 = var10.length;
//
//                        for(int var9 = 0; var9 < var11; ++var9) {
//                            NetWorkType temp_netWorkType = var10[var9];
//                            if (temp_netWorkType.getType() == invoke) {
//                                netWorkType = temp_netWorkType;
//                                break;
//                            }
//                        }
//                    } catch (Exception var12) {
//                    }
//                }
//            } else {
//                netWorkType = SysUtils.NetWorkType.NoNetConnection;
//            }
//
//            return netWorkType;
//        }
//    }
    public final boolean is5GHz(int freq) {
        return freq > 4900 && freq < 5900;
    }

    public final boolean is24GHz(int freq) {
        return freq > 2400 && freq < 2500;
    }
    public final String appVersionCode(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        PackageManager var10000 = context.getPackageManager();
        Intrinsics.checkNotNullExpressionValue(var10000, "context.packageManager");
        PackageManager packageManager = var10000;

        try {
            PackageInfo var5 = packageManager.getPackageInfo(context.getPackageName(), 0);
            Intrinsics.checkNotNullExpressionValue(var5, "packageManager.getPackag…o(context.packageName, 0)");
            PackageInfo packageInfo = var5;
            return String.valueOf(packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException var4) {
            var4.printStackTrace();
            return "";
        }
    }
    public final String getVersionName(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String var10000 = packageInfo.versionName;
            Intrinsics.checkNotNullExpressionValue(var10000, "packageInfo.versionName");
            return var10000;
        } catch (Exception var4) {
            var4.printStackTrace();
            return "";
        }
    }
    public final boolean isInstallApk(@NotNull String pkgname) {
        Intrinsics.checkNotNullParameter(pkgname, "pkgname");
        List var10000 = DemoApplication.getApplication().getPackageManager().getInstalledPackages(0);
        Intrinsics.checkNotNullExpressionValue(var10000, "ProjectContext.INSTANCE.…r.getInstalledPackages(0)");
        List packages = var10000;
        Iterator var4 = packages.iterator();

        PackageInfo packageInfo;
        do {
            if (!var4.hasNext()) {
                return false;
            }

            packageInfo = (PackageInfo)var4.next();
        } while(!packageInfo.packageName.equals(pkgname));

        return true;
    }

    private SysUtils() {
    }

    static {
        SysUtils var0 = new SysUtils();
        INSTANCE = var0;
    }
//    public static enum NetWorkType {
//        NETWORK_UNKNOWN ,
//        MOBILE_2G,
//        MOBILE_3G,
//        MOBILE_4G,
//        WIFI,
//        NoNetConnection;
//
//        private int type;
//        @NotNull
//        private String netName;
//
//        public final int getType() {
//            return this.type;
//        }
//
//        public final void setType(int var1) {
//            this.type = var1;
//        }
//
//        @NotNull
//        public final String getNetName() {
//            return this.netName;
//        }
//
//        public final void setNetName(@NotNull String var1) {
//            Intrinsics.checkNotNullParameter(var1, "<set-?>");
//            this.netName = var1;
//        }
//
//        private NetWorkType(int type, String netName) {
//            this.type = type;
//            this.netName = netName;
//        }
//    }
}
