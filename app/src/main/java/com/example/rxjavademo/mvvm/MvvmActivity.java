package com.example.rxjavademo.mvvm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import com.example.rxjavademo.R;
import com.example.rxjavademo.databinding.MvvmBinding;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
class MvvmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MvvmBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        binding.setUser(new UserBean());
    }
}
