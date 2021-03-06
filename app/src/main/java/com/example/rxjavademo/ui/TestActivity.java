package com.example.rxjavademo.ui;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.JobIntentService;

import com.example.rxjavademo.R;
import com.example.rxjavademo.buildermode.Bean;
import com.example.rxjavademo.buildermode.MyOkhttp;
import com.example.rxjavademo.mvp.MvpActivity;
import com.example.rxjavademo.other.MyBroadCastReceiver;
import com.example.rxjavademo.other.MyService;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnTest;
    private TextView tvTest;

    private MyBroadCastReceiver receiver;

    private MyService.SimpleBinder mBinder;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("dss","---------"+name);
            mBinder = (MyService.SimpleBinder) service;
            mBinder.doTask();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder.stopTask();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private void initView(){
        btnTest = findViewById(R.id.btn_test);
        tvTest = findViewById(R.id.tv_test);
        btnTest.setOnClickListener(this);
        tvTest.setOnClickListener(this);
    }

    private void initData(){

    }


    private void testBroadCast(){
        receiver = new MyBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("c.b.a");
        this.registerReceiver(receiver,intentFilter);


        Intent intent = new Intent();
        intent.setAction("c.b.a");
        intent.putExtra("data","??????");
        sendBroadcast(intent);

    }

    private void startMyService(){
        Intent intent = new Intent(this,MyService.class);
        //start????????????
        this.startService(intent);
    }



    private void stopMyService(){
        Intent intent = new Intent(this,MyService.class);
        this.stopService(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test:
//                startMyService();
                //bind????????????
//                Intent intent = new Intent(TestActivity.this, MyService.class);
//                Log.d("dss","????????????"+ this.bindService(intent, serviceConnection,this.BIND_AUTO_CREATE));
                startActivity(new Intent(TestActivity.this, MvpActivity.class));
                break;
            case R.id.tv_test:
//                stopMyService();
                unbindService(serviceConnection);
                break;
            default:
                break;
        }
    }
}
