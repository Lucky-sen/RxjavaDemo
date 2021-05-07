package com.example.rxjavademo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final  String TAG = "RxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testJust();
    }

    private void testCreate(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        })
//                .observeOn(AndroidSchedulers.mainThread())              处理线程为主线程
                .subscribeOn(Schedulers.io())          //  处理线程为 eg：io线程
                .subscribe(new Observer<Integer>(){

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "开始执行事件"+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "报错");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "完成");
            }
        });
    }

    private void testJust(){
       Observable.just(1,2,3,4).subscribe(new Observer<Integer>(){
           @Override
           public void onSubscribe(Disposable d) {
               Log.d(TAG, "开始采用subscribe连接");
           }

           @Override
           public void onNext(Integer value) {
               Log.d(TAG, "开始执行事件"+value);
           }

           @Override
           public void onError(Throwable e) {
               Log.d(TAG, "报错");
           }

           @Override
           public void onComplete() {
               Log.d(TAG, "完成");
           }
       });
    }
}