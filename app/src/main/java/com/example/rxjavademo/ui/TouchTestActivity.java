package com.example.rxjavademo.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.example.rxjavademo.R;
import com.example.rxjavademo.widget.DispathButton;

/**
 * 作者：sen.dong
 * 日期时间: 2021/5/28 10:53
 * 内容描述: touch测试
 */
public class TouchTestActivity extends ComponentActivity {

    private RelativeLayout rlBg;
    private DispathButton btnTouch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_touch_test);
        rlBg = findViewById(R.id.rl_bg);
        btnTouch = findViewById(R.id.btn_touch);

        rlBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test", "RL 点击事件");
            }
        });

        btnTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test", "btnTouch 点击事件");
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("test", "Activity onTouchEvent" + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("test", "Activity dispatchTouchEvent" + super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }


}
