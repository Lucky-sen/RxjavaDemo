package com.example.rxjavademo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavademo.R;
import com.example.rxjavademo.network.GzipRequestInterceptor;
import com.example.rxjavademo.network.Constant;
import com.example.rxjavademo.network.TestInterceptor;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * 作者：sen.dong
 * 日期时间: 2021/5/10 16:36
 * 内容描述:
 */
public class DemoOneActivity extends AppCompatActivity {

    private final String TAG = "DemoOneActivity";
    private Button btnRequest;
    private TextView tvResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_one);
        btnRequest = findViewById(R.id.btn_request);
        tvResponse = findViewById(R.id.tv_response);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String url = Constant.baseUrl + Constant.getAllAds;
                requestByPost();
            }
        });
    }

    private void requestDataByGet(String url){
        //创建OkHttpClient客户端
        //OkHttp可以设置调用、连接和读写的超时时间，都是通过OkHttpClient.Builder设置的,同时也可以添加interceptors拦截器,设置缓存
        OkHttpClient client = new OkHttpClient.Builder()
                                .addInterceptor(new GzipRequestInterceptor())
                                .build();
        //创建请求Request
        //在request中设置 header请求头
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        //通过client和request 创建一个RealCall
        Call call = client.newCall(request);
        Log.e(TAG, "打印出请求的地址URL=" + url);
        //通过call 进行异步网络请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = "返回code" + response.code() + "body" + response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResponse.setText(str);
                    }
                });
                Log.e(TAG, str);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                //通过call 进行同步网络请求
                try {
                    Response response = call.execute();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestByPost() {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        Request request = new Request.Builder().post(RequestBody.create(mediaType,"i am sen"))
                                                .url("https://api.github.com/markdown/raw")
                                                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG,"Response body:" + response.body().string());
                Headers headers = response.headers();
                for(int i=0;i<headers.size();i++){
                    Log.e(TAG,headers.name(i)+ ":" + headers.value(i));
                }
            }
        });
    }

    //自定义拦截器
    private class LoggingIntercepor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request= chain.request();
            Response response = chain.proceed(request);

            return null;
        }
    }
}
