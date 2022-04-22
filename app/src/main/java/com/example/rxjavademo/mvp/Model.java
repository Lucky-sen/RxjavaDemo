package com.example.rxjavademo.mvp;

/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
class Model {
    private IView mIVIew;

    public Model(IView mIVIew) {
        this.mIVIew = mIVIew;
    }

    private void login(){
        mIVIew.loginResult();
    }

}
