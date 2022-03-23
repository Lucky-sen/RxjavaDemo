package com.example.rxjavademo.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            if(null != intent){
                Log.d("dssss","接收到的广播"+intent.getAction()+"接受到广播的参数"+intent.getExtras().getString("data"));
            }
    }
}
