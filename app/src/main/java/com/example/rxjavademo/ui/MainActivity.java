package com.example.rxjavademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavademo.R;
import com.example.rxjavademo.optimization.RecyclePictureActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final  String TAG = "MainActivity";

    private Button btnOne;
    private Button btnView;
    private Button btnDispatch;
    private Button btnRxjava;
    private Button btnFree;
    private Button btnTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOne = findViewById(R.id.btn_one);
        btnView = findViewById(R.id.btn_view);
        btnDispatch = findViewById(R.id.btn_dispatch);
        btnRxjava = findViewById(R.id.btn_rxjava);
        btnFree = findViewById(R.id.btn_free);
        btnTest = findViewById(R.id.btn_test);

        btnOne.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnDispatch.setOnClickListener(this);
        btnRxjava.setOnClickListener(this);
        btnFree.setOnClickListener(this);
        btnTest.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_one:
                Intent intent = new Intent(MainActivity.this,DemoOneActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_view:
                Intent intent1 = new Intent(MainActivity.this,CustomViewActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_dispatch:
                Intent intent2 = new Intent(MainActivity.this,TouchTestActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_rxjava:
                Intent intent3 = new Intent(MainActivity.this,RxTestActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_free:
                Intent intent4 = new Intent(MainActivity.this,MyNetWorkTestActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_test:
//                Intent intent5 = new Intent(MainActivity.this,TestActivity.class);
//                startActivity(intent5);
                Intent intent5 = new Intent(MainActivity.this, RecyclePictureActivity.class);
                startActivity(intent5);
                break;
        }
    }
}