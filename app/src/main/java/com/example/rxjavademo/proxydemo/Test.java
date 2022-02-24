package com.example.rxjavademo.proxydemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 作者：admin016
 * 日期时间: 2022/2/22 17:10
 * 内容描述:
 */
class Test {
    public static void main(String[] args) {
        IStars iStars = new Stars("Frank");
        InvocationHandler handler = new ProxyStars(iStars);
        IStars proxy = (IStars) Proxy.newProxyInstance(iStars.getClass().getClassLoader(), iStars.getClass().getInterfaces(), handler);
        proxy.dance();
        proxy.sing();
    }
}
