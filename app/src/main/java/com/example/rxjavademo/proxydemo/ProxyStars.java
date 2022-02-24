package com.example.rxjavademo.proxydemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 作者：admin016
 * 日期时间: 2022/2/22 17:03
 * 内容描述:
 */
public class ProxyStars implements InvocationHandler {

    private Object object;

    public ProxyStars(Object object) {
        this.object = object;
    }

    private boolean runBefore(Method method){
        System.out.println("拦截到代理请求");
        if("dance".equals(method.getName())){
            System.out.println("明星受伤了不能跳舞了");
            return false;
        }
        return true;
    }

    private void runAfter(Method method){
        System.out.println(method.getName());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(!runBefore(method)){
            return null;
        }
        Object result = method.invoke(object, args);
        runAfter(method);
        return result;
    }
}
