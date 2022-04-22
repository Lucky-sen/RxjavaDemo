package com.example.rxjavademo.widget;

import android.content.Context;
import android.view.ViewGroup;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
class MyGroupView extends ViewGroup {
    public MyGroupView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
