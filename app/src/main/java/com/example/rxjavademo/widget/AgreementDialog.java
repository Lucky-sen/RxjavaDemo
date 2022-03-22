package com.example.rxjavademo.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rxjavademo.R;


/**
 * @author dingyx
 * @description: 通用协议 dialog
 * @date: 2020/8/11
 */
public class AgreementDialog implements View.OnClickListener {

    private Dialog dialog;
    private Context context;

    private TextView tvTitle;
    private TextView tvContent;
    private ImageView ivClose;

    public AgreementDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.ChooseDialog);
        initView();
    }

    private void initView() {
        dialog.setContentView(R.layout.dialog_agreement);
        tvTitle = dialog.findViewById(R.id.tv_title);
        tvContent = dialog.findViewById(R.id.tv_content);
        ivClose = dialog.findViewById(R.id.iv_close);

        ivClose.setOnClickListener(this);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        // 设置dialog占满父容器
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        // 设置点击外围消散
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }

    public void setAgreementInfo(String title, String content) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
        if (tvContent != null) {
            tvContent.setText(content);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void show() {
        dialog.show();
    }

}

