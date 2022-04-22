package com.example.rxjavademo.buildermode;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */

public class Bean {
    private String name;
    private String age;

    public Bean(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public static class Builder {
        private String name;
        private String age;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(String age) {
            this.age = age;
            return this;
        }

        public Bean build() {
            return new Bean(name, age);
        }
    }
}
