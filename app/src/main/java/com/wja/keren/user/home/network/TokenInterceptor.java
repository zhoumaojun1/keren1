package com.wja.keren.user.home.network;

import androidx.annotation.NonNull;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.wja.keren.DemoApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class TokenInterceptor implements Interceptor {
    private String token; //用于添加的请求头
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        SharedPreferences sharedPreferences = DemoApplication.getApplication().getSharedPreferences("cache", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        if (token.isEmpty()) {
            //表示第一次登陆还没拉取过token
            return chain.proceed(originalRequest);
        }else {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            Log.d("已经授权intercept",token);
            Response response = chain.proceed(request);

            if (response.code() == 401) {//返回为token失效
              //  refreshToken();//重新获取token，此处的刷新token需要同步执行以防止异步到来的问题
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", token)
                        .build();
                response.close();
                return chain.proceed(newRequest);
            }
            return response;
        }


    }
}
