package com.example.rxjavademo.asynccode;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion: 单列模式，避免这个对象会重复的创建和销毁
 */
public class Singleton {

    private static Singleton singleton;

    private Singleton(){}

    /**
     * 懒汉模式 没有考虑线程安全的问题
     * @return
     */
//    public static Singleton getInstance(){
//         if(singleton == null){
//             singleton = new Singleton();
//         }
//         return singleton;
//    }

    /**
     * 懒汉模式  对静态方法加锁，线程安全,对静态方法进行一个加锁
     * @return
     */
//    public static synchronized  Singleton getInstance(){
//        if(singleton == null){
//            singleton = new Singleton();
//        }
//        return singleton;
//    }

    /**
     * 双锁/双重校验锁
     * @return
     */
    public static Singleton getInstance(){
        if(singleton == null){
            synchronized (Singleton.class){
                if(singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }



}
