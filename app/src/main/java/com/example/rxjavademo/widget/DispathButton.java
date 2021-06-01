package com.example.rxjavademo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 作者：sen.dong
 * 日期时间: 2021/6/1 10:52
 * 内容描述:
 */
public class DispathButton extends androidx.appcompat.widget.AppCompatButton {
    public DispathButton(@NonNull Context context) {
        super(context);
    }

    public DispathButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispathButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("test", "Button dispatchTouchEvent" + super.dispatchTouchEvent(event));
        return super.dispatchTouchEvent(event);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("test", "Button onTouchEvent" + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }
}
