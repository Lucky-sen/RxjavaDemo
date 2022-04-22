package com.example.rxjavademo.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavademo.R;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
public class MvpActivity extends AppCompatActivity implements IView{

    private Controller controller = new Controller();
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void loginResult() {

    }
}
