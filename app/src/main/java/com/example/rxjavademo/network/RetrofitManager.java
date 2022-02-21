package com.example.rxjavademo.network;

import com.example.rxjavademo.bean.BaseBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：sen.dong
 * 日期时间: 2021/5/7 15:54
 * 内容描述:Retrofit管理类
 */
public class RetrofitManager {
    private Api api;
    private static RetrofitManager retrofitManager;

    private RetrofitManager(){
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.addInterceptor(new TestInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .client(okBuilder.build())
                .baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);
        api.getList().subscribe(new Observer<BaseBean<String>>(){

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseBean<String> value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public Api service ( ) {
        return api;
    }

}
