package com.wja.keren.user.home.network;


import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import pub.devrel.easypermissions.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HtlUserRetrofit {
    private static final String TAG = HtlUserRetrofit.class.getName();

    public static final String WJA_LOGIN_ON_LOCATION_USER_URL = "http://172.16.102.53/auth/";

    public static final String WJA_LOGIN_ON_LOCATION_CARD_URL = "http://172.16.102.53/manager/";

     public static final String WJA_LOGIN_ON_LINE_USER_URL = "http://121.40.35.88/auth/";

    public static final String WJA_LOGIN_ON_LINE_CARD_URL = "http://121.40.35.88/manager/";



    private static final int DEFAULT_TIMEOUT = 15000;

    public static final String CONNECT_TIMEOUT = "CONNECT_TIMEOUT";
    public static final String READ_TIMEOUT = "READ_TIMEOUT";
    public static final String WRITE_TIMEOUT = "WRITE_TIMEOUT";
    private HtlService service;
    public static HtlUserRetrofit getInstance() {
        return Inner.getInstance();
    }


    public HtlService getService(int port) {
        return service;
    }

    private static class Inner {
        private static HtlUserRetrofit api = new HtlUserRetrofit();

        public static HtlUserRetrofit getInstance() {
            return api;
        }
    }
    private HtlUserRetrofit() {
        Retrofit retrofit;
        Interceptor timeoutInterceptor = chain -> {
            Request request = chain.request();

            int connectTimeout = chain.connectTimeoutMillis();
            int readTimeout = chain.readTimeoutMillis();
            int writeTimeout = chain.writeTimeoutMillis();

            String connectNew = request.header(CONNECT_TIMEOUT);
            String readNew = request.header(READ_TIMEOUT);
            String writeNew = request.header(WRITE_TIMEOUT);
            Request.Builder builder = request.newBuilder();
            HttpUrl oldHttpUrl = request.url();
            HttpUrl   newBaseUrl = HttpUrl.parse(WJA_LOGIN_ON_LINE_USER_URL);
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    //.port(80)
                    .build();

            if (!TextUtils.isEmpty(connectNew)) {
                connectTimeout = Integer.parseInt(connectNew);
            }
            if (!TextUtils.isEmpty(readNew)) {
                readTimeout = Integer.parseInt(readNew);
            }
            if (!TextUtils.isEmpty(writeNew)) {
                writeTimeout = Integer.parseInt(writeNew);
            }
            Response response = chain
                    .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                    .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                    .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                    .proceed(builder.url(newFullUrl).build());
            return response;
        };

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String log) {
                //打印retrofitLog日记
                Log.d(TAG, "RetrofitLog = " + log);
            }
        });
        if (BuildConfig.DEBUG) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        //设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .addInterceptor(timeoutInterceptor)
                .addInterceptor(logInterceptor)
               .addInterceptor(new TokenInterceptor());

            OkHttpClient okHttpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(WJA_LOGIN_ON_LINE_USER_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();

            service = retrofit.create(HtlService.class);
            OkHttpClient.Builder newBuilder = okHttpClient.newBuilder()
                    .readTimeout(8, TimeUnit.SECONDS)
                    .socketFactory(SocketFactory.getDefault());
            //移除其他拦截器
            List<Interceptor> interceptors = newBuilder.interceptors();
            interceptors.clear();



    }



    /**
     * 线程切换
     *
     * @param f    需要线程切换的任务
     * @param type 0:子->主   1:主->主  2:不变 3:主->子 4:子->子
     * @param <T>  任务类型
     * @return 线程切换后任务
     */

    private <T> Observable<T> threadConvert(Observable<T> f, int type) {
        Observable<T> t;
        switch (type) {
            case 0:
                t = f.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                break;
            case 1:
                t = f.subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
                break;
            case 2:
                t = f;
                break;
            case 3:
                t = f.subscribeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.io());
                break;
            case 4:
                t = f.subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
                break;
            default:
                t = f.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                break;
        }

        return t;
    }


}
