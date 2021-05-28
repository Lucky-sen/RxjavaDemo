package com.example.rxjavademo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavademo.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    private void requestDataByGet(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Log.e(TAG, "打印出请求的地址URL=" + url);
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
}
