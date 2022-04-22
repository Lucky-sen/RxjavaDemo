package com.example.rxjavademo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavademo.R;

import java.io.IOException;
import java.lang.reflect.Proxy;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：admin016
 * 日期时间: 2022/2/16 14:07
 * 内容描述:
 */
public class MyNetWorkTestActivity extends AppCompatActivity {

    private Button btnRequest;
    private EditText editUrl;
    private TextView tvResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);
        initView();
        Handler handler10 = new Handler(Looper.myLooper(),new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Log.d("sssssssssss","handler10接受到了消息");
                return false;
            }
        });


        new Thread(new Runnable(){
            @Override
            public void run() {
                handler10.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();

        Handler handler20 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Log.d("sssssssssss","handler20接受到消息");
                return false;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                handler20.sendMessage(msg);
            }
        }).start();

    }



    private void initView(){
        btnRequest = findViewById(R.id.btn_request);
        editUrl = findViewById(R.id.edit_url);
        tvResponse = findViewById(R.id.tv_response);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editUrl.getText().toString().trim())){
                    showToast("URL 为空，无法请求");
                }else {
                    requestData(editUrl.getText().toString().trim());
                }
            }
        });
    }

    private void requestData(String url){
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().get()
                                               .url(url)
                                               .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("ssssssssssss", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("sssssssssss", response.body().string());

            }
        });
    }

    private void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    private Handler handler1 = new Handler();


    private void sonThread(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.arg1 = 1;
                Bundle bundle = new Bundle();
                bundle.putString("key", "value");
                msg.setData(bundle);
                handler1.sendMessage(msg);
            }
        });
    }

    //        new Thread(new Runnable(){
//
//            @Override
//            public void run() {
//                //加上这个就不会崩溃，因为创建handler的时候，回去判断是否有Looper对象，没有则会抛出异常
//                Looper.prepare();
//                Handler handler2 = new Handler(new Handler.Callback() {
//                    @Override
//                    public boolean handleMessage(@NonNull Message msg) {
//                        return false;
//                    }
//                });
//            }
//        }).start();

}
