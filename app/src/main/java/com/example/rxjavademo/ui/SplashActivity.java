package com.example.rxjavademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavademo.R;

/**
 * 作者：admin016
 * 日期时间: 2022/3/15 14:23
 * 内容描述:
 * @author admin016
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(1, 1000);
    }



    Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };


    private void testHandler(){
        //设置异步消息
//        Message message = Message.obtain();
//        message.setAsynchronous(true);
//        Handler handler1 = Handler.createAsync() 创建异步消息handler


        //获取当前Looper的消息队列
        MessageQueue messageQueue = Looper.myQueue();
        
        messageQueue.addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                //启动Activity的时候 会有一些延时操作可以放在这里
                return false;  //当IdelHandler接口返回false时，表示该IdelHandler只执行一次，
            }
        });
    }

}
