package com.example.rxjavademo.asynccode;

import android.provider.ContactsContract;

import java.util.concurrent.Callable;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
class ThreadDemo {

    /**
     *  线程的五种状态：
     *  1.新建 new Thread
     *  2.就绪状态 runnable
     *  3.running 运行状态  thread.start()开启
     *  4.死亡 dead 被系统杀死或者执行完毕
     *  5.block 阻塞状态  正在睡眠sleep（long l）休眠时间到了就会重新唤起，wait（）方法
     *
     *  线程的中断：
     *  1.线程中处理完run（）方法后自动就会结束
     *  2.调用线程的stop（）方法，但是这种被废弃了，这种方法是不安全的 因为stop（）执行之后会停止run（）方法里面的内容，并且抛出ThreadDeath异常
     *    就会倒置一些问题，比如数据还没有关闭，文件读写的问题，而且会立即释放所有的锁，如果是多进程的话，会出现数据混乱的问题。
     *  3.intercept（）方法来执行线程的中断针对于线程 sleep 或者wait的情况会抛出一个异常，然后再catch中去处理逻辑
     *  它的实现逻辑并不是直接来完成线程的中断，而是在线程中完成一个标记，我们可以通过thread.isIntercept（）来判断
     *  4.标记法，通过一个flag， 在线程执行的时候判断flag为true的时候 执行run里面的代码，当要去中断的时候，通过flag = false来跳出线程执行的处理
     *
     *  sleep 与 wait 的区别：
     *  1.sleep（）是线程中的方法 ，wait（）是java中object的方法
     *  2.sleep（）不会释放锁，但是wait（）会释放锁，而且还会进入等待队列
     *  3.sleep（）不依赖sychronized，wait（）依赖于sychronized
     *  4.sleep（）不需要唤醒休眠之后推出阻塞，wait（）需要被唤醒*（释放锁）线程挂起 ，调用notify唤醒（）有机会去获得锁
     *
     *
     */

    /**
     * 通过runable 创建一个线程
     */
    private Runnable thread1 = new Runnable() {
        @Override
        public void run() {

        }
    };

    /**
     * 这种方式是有返回值的 注意
     */
    private Callable thread2 = new Callable() {
        @Override
        public Object call() throws Exception {
            return null;
        }
    };


    /**
     * 通过继承Thread创建一个线程
     */
    public class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
        }
    }


}
