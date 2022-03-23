package com.example.rxjavademo.other;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
public class MyService extends Service {

    private Binder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("dss","onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("dss","onStartCommand()");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("dss","onDestroy()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(null != mBinder){
            return mBinder;
        }
        return null;
    }

    public class SimpleBinder extends Binder {
        public void doTask(){
            Log.d("dss","执行任务");
        }

        public void stopTask(){
            Log.d("dss","结束任务");
        }
    }
}
