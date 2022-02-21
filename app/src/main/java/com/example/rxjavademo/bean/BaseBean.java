package com.example.rxjavademo.bean;

/**
 * 作者：admin016
 * 日期时间: 2021/7/7 15:10
 * 内容描述: 网络请求基类
 */
public class BaseBean<T>{
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
