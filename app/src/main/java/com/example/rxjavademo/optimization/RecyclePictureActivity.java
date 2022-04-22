package com.example.rxjavademo.optimization;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rxjavademo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
public class RecyclePictureActivity  extends AppCompatActivity {

    private RecyclerView rv;
    private PicAdapter adapter;
    private List<String> urls = new ArrayList<>();

    private static Inner inner;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclepic);
        rv = findViewById(R.id.recycle_view);
        initData();
        adapter = new PicAdapter(this,urls);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        doOptimization();
        inner = new Inner();
    }

    private void initData(){
        for(int i = 0;i<50;i++){
            if(i%2 == 0){
                urls.add("https://upload-images.jianshu.io/upload_images/5809200-a99419bb94924e6d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
            }else {
                urls.add("https://upload-images.jianshu.io/upload_images/5809200-736bc3917fe92142.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
            }
        }
    }

    private void doOptimization(){
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull  RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    Glide.with(RecyclePictureActivity.this).pauseRequests();
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        Glide.with(RecyclePictureActivity.this).resumeRequests();
                }
            }

            @Override
            public void onScrolled(@NonNull  RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    class Inner{

    }
}
