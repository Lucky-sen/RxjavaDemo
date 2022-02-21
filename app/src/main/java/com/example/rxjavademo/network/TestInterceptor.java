package com.example.rxjavademo.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：admin016
 * 日期时间: 2021/6/28 13:53
 * 内容描述:
 */
public class TestInterceptor implements Interceptor {

    private final String TAG = "test_interceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(TAG, "进入 Response 方法");
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.d(TAG,String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        // 发起HTTP请求，并获取响应对象
        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d(TAG, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}
