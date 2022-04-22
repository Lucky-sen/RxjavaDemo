package com.example.rxjavademo.optimization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rxjavademo.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicHolder> {

    private Context context;
    private List<String> urls;

    public PicAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @NonNull
    @Override
    public PicHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycle_pic,null);
        return new PicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  PicAdapter.PicHolder holder, int position) {
        Glide.with(context).load(urls.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }


    public class PicHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public PicHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv);
        }
    }
}
