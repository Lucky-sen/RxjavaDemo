package com.example.rxjavademo.asynccode;

import android.os.AsyncTask;


/**
 * author:sen
 * email:562605446@qq.com
 * describtion:
 */
class MyAsyncTask extends AsyncTask {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
