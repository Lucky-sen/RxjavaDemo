package com.example.rxjavademo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.rxjavademo.R;

/**
 * 作者：sen.dong
 * 日期时间: 2021/5/24 15:28
 * 内容描述: 自定义进度view
 */
public class MyProgressView extends View {

    private int backgroundColor;//底部背景色
    private int currentColor; //当前颜色
    private int progressColor; //进度颜色
    private Paint bPaint;//背景进度画笔
    private Paint cPaint;//滑块进度画笔
    private Paint paint;//进度画笔
    private Paint textPaint;
    private RectF rectf_b;//背景圆角矩形
    private RectF rectf_c;//滑块圆角矩形
    private RectF rectf_p;//进度圆角矩形
    private int viewWidth, viewHeight;
    private float progress = 0;//进度值
    private float x, y;
    public ProgressListener progressListener;
    private String progressStr;
    private Bitmap circleBitmap,progressBitmap;


    public MyProgressView(Context context) {
        super(context);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
        initPaint();
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //计算宽
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = 750;
        }
        //计算高
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 80;
        }
        viewWidth = width;
        viewHeight = height;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //step1:画背景
        if (rectf_b == null) {
            rectf_b = new RectF(0, 15, viewWidth, viewHeight - 15);
        }
        canvas.drawRoundRect(rectf_b, 10, 10, bPaint);
        //step2:画进度
        if (progress > 0) {
            rectf_p = new RectF(0, 15, viewWidth * progress / 100, viewHeight - 15);
        } else {
            rectf_p = new RectF(0, 15, 0, viewHeight - 15);
        }

        canvas.drawRoundRect(rectf_p, 10, 10, paint);
        //step3:画滑块
        if (progress > 0) {
            rectf_c = new RectF(viewWidth * progress / 100 - 50, 0, viewWidth * progress / 100, viewHeight);
        } else {
            rectf_c = new RectF(0, 0, 50, viewHeight);
        }
        canvas.drawRoundRect(rectf_c, 10, 10, cPaint);
        progressStr = "当前进度：" + progress + "%";
        canvas.drawText(progressStr, 200, 200, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            if (x >= viewWidth) {
                progress = 100;
            } else if (x <= 50) {
                progress = 0;
            } else {
                progress = x * 100 / viewWidth;
            }

        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            x = event.getX();
            y = event.getY();
            if (x >= viewWidth) {
                progress = 100;
            } else if (x <= 50) {
                progress = 0;
            } else {
                progress = x * 100 / viewWidth;
            }
        }
//        progressListener.getProgress(progress);
        invalidate();
        return true;
    }

    public void startAnim(int count) {
        ValueAnimator anim = ValueAnimator.ofFloat(0, count);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progress = (Float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(2000);
        anim.setInterpolator(new LinearInterpolator());
        anim.start();
    }

    /**
     * 初始化属性
     * @param context
     * @param attrs
     */
    private void init(Context context,AttributeSet attrs){
         if(attrs!=null){
             TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyProgressView_style);
             backgroundColor = typedArray.getColor(R.styleable.MyProgressView_style_background_color,getResources().getColor(R.color.design_default_color_secondary));
             currentColor = typedArray.getColor(R.styleable.MyProgressView_style_current_color,getResources().getColor(R.color.design_default_color_primary));
             progressColor = typedArray.getColor(R.styleable.MyProgressView_style_progress_color, getResources().getColor(R.color.design_default_color_error));
             typedArray.recycle();
         }
    }

    /**
     * 初始化画笔
     */
    private void initPaint(){
        bPaint = new Paint();
        bPaint.setAntiAlias(true);
        bPaint.setColor(backgroundColor);
        bPaint.setStyle(Paint.Style.FILL);
        bPaint.setStrokeCap(Paint.Cap.ROUND);

        cPaint = new Paint();
        cPaint.setAntiAlias(true);
        cPaint.setColor(currentColor);
        cPaint.setStyle(Paint.Style.FILL);
        cPaint.setStrokeCap(Paint.Cap.ROUND);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(progressColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getResources().getColor(R.color.white));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    public interface ProgressListener {
        void getProgress(float progress);
    }

    public void setProgressListener(ProgressListener listener) {
        progressListener = listener;
    }


}
