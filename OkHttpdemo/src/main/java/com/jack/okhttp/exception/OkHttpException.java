package com.jack.okhttp.exception;

/**
 * 自定义的网络请求异常
 * Created by xuning on 17/9/3.
 */
public class OkHttpException extends Exception{

    private int mCode;
    private String mMsg;



    public OkHttpException(int mCode, String mMsg) {
        this.mCode = mCode;
        this.mMsg = mMsg;
    }
}
