package com.example.rxjavademo.asynccode;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
public class Singleton2 {

    //类加载的时候就对他进行了初始化
    private static Singleton2 singleton2 = new Singleton2();

    private Singleton2(){}

    public static Singleton2 getInstance(){
        return singleton2;
    }

}
