package com.example.rxjavademo.asynccode;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
class OrderBean<T> {
    public OrderBean(){
    }

    private T t;
    private T[] array = (T[]) new Object[10];
    private List<T> list1 = new LinkedList<>();

    /**
     * 相当于返回了一个没有名字的
     */
    private MyInterface myInterface = new MyInterface() {
        @Override
        public void doSomething() {

        }

        @Override
        public void getResult() {

        }
    };

    /**
     * 静态方法中不能用泛型
     * @param t
     */
//    public static void show(T t){
//        Log.d("test",""+t);
//    }

    /**
     * 泛型方法返回
     * @param array
     * @return
     */
    public List<T> getList(T[] array){
//        List<T> list = new ArrayList<>();
        for(T t : array){
            list1.add(t);
        }
        return list1;
    }

    public T getT(T a){
        return t;
    }
}
