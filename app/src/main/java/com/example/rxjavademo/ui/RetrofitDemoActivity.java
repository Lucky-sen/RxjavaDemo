package com.example.rxjavademo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavademo.network.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 日期时间: 2022/2/21 16:48
 * 内容描述:
 * @author admin016
 */
public class RetrofitDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initNetWork(){
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://ask-test.ihicon-wh.com/api/")
                                .addConverterFactory(GsonConverterFactory.create())
//                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .build();
        retrofit.create(Api.class);

    }
}
