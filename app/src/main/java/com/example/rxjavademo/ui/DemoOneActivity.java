package com.example.rxjavademo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavademo.R;
import com.example.rxjavademo.network.Constant;
import com.example.rxjavademo.network.TestInterceptor;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
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
                String getUrl = Constant.baseUrl + Constant.getAllAds;
                String postUrl = Constant.baseUrl + Constant.postCheckVersion;
                requestDataByGet(getUrl);
//                requestByPost(postUrl);
//                RequestPostByio(postUrl);
//                requestPostFile();
            }
        });
    }

    /**
     * get请求
     *
     * @param url
     */
    private void requestDataByGet(String url) {
        //step1 构建一个网络类型的实例，一般会将所有的网络请求使用同一个单例对象。
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TestInterceptor())
                .addNetworkInterceptor(new TestInterceptor())
                .build();
        //step2 构建一个具体的网络请求对象，具体的请求url，请求头，请求体等等。
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("terminal", "1")
                .addHeader("timestamp", "1624601441602")
                .addHeader("appVersion", "2.0.0")
                .addHeader("appCode", "13")
                .addHeader("sign", "ad78bab18887414640ec596e43f414076831588d9394ed42e1faa2334759062f")
                .build();

        //step3 新的call request
        Call call = client.newCall(request);
        Log.e(TAG, "打印出请求的地址URL=" + url);
        //step4 发送请求
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


    /**
     * post 请求
     */
    private void requestByPost(String url) {
        //step1 创建客户端
        OkHttpClient client = new OkHttpClient();
        //step2 用于描述请求/响应body的内容类型
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        //step3 创建请求
        Request request = new Request.Builder().post(RequestBody.create(mediaType, "i am sen"))
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "code:" + response.code() + "Response body:" + response.body().string());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.e(TAG, headers.name(i) + ":" + headers.value(i));
                }
            }
        });
    }

    /**
     * post请求请求流
     * @param url
     */
    private void RequestPostByio(String url){
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("i am sen");
            }
        };
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "code:"+ response.code() + "responseBody:"+response.body().toString());
                for(int i=0;i<response.headers().size();i++){
                    Log.d(TAG, response.headers().name(i)+":"+response.headers().value(i));
                }
            }
        });
    }

    /**
     * 提交文件
     */
    private void requestPostFile(){
        OkHttpClient client = new OkHttpClient();
        MediaType type = MediaType.parse("text/x-markdown; charset=utf-8");
        File file = new File("test.md");
        Request request = new Request.Builder().url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(type,file))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"code:"+response.code());
                Log.d(TAG,response.body().toString());
            }
        });
    }

    /**
     * post提交表单
     */
    private void requestPostFrom(){
        RequestBody requestBody = new FormBody.Builder()
                .add("params1", "params")
                .add("params2", "params")
                .build();
    }

    /**
     * 拦截器
     * ①一类是全局的 interceptor，该类 interceptor 在整个拦截器链中最早被调用，通过 OkHttpClient.Builder#addInterceptor(Interceptor) 传入；
     * ②另外一类是非网页请求的 interceptor ，这类拦截器只会在非网页请求中被调用，并且是在组装完请求之后，真正发起网络请求前被调用，
     * 所有的 interceptor 被保存在 List<Interceptor> interceptors 集合中，按照添加顺序来逐个调用
     */
    private void interceptorTest(){

    }
}
