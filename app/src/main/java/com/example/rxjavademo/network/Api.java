package com.example.rxjavademo.network;

import com.example.rxjavademo.bean.VoucherLabelBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 作者：sen.dong
 * 日期时间: 2021/5/7 15:12
 * 内容描述:
 */
public interface Api {
    String API_URL = "https://ask-test.ihicon-wh.com/api/";

    @GET("order-front/voucher/class/list")
    Call<VoucherLabelBean> getVoucherLabels();
}
