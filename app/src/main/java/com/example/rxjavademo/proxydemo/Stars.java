package com.example.rxjavademo.proxydemo;

/**
 * 作者：admin016
 * 日期时间: 2022/2/22 17:01
 * 内容描述: 被代理类
 */
public class Stars implements IStars{

    public Stars(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void dance() {
       System.out.println("跳了一支舞");
    }

    @Override
    public void sing() {
        System.out.println("唱了一首歌");
    }
}
