package com.example.rxjavademo.buildermode;

import android.net.Uri;

import java.util.List;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
public class MyOkhttp {
    private String dispatcher;
    private String proxy;
    private String connection;
    private List<String> interceptor;
    private List<String> networkInterceptor;



    public MyOkhttp(Builder builder){
        this.dispatcher = builder.dispatcher;
        this.proxy = builder.proxy;
        this.connection = builder.connection;
        this.interceptor = builder.interceptor;
        this.networkInterceptor = builder.networkInterceptor;
    }

    public Builder build(){
        return new Builder();
    }


    public final static class Builder{
        private String dispatcher;
        private String proxy;
        private String connection;
        private List<String> interceptor;
        private List<String> networkInterceptor;

        public Builder(){
            dispatcher =  "";
            proxy = "";
            connection = "";
            interceptor = null;
            networkInterceptor = null;
        }

        Builder(MyOkhttp myOkhttp){
            this.dispatcher = myOkhttp.dispatcher;
            this.proxy = myOkhttp.proxy;
            this.connection = myOkhttp.connection;
            this.interceptor = myOkhttp.interceptor;
            this.networkInterceptor = myOkhttp.networkInterceptor;
        }

        public Builder setDispatcher(String dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        public Builder setProxy(String proxy) {
            this.proxy = proxy;
            return this;
        }

        public MyOkhttp build(){
            return new MyOkhttp(this);
        }

    }

}
