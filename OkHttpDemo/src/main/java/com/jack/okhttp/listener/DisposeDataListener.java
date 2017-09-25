package com.jack.okhttp.listener;

/**
 * 获取到数据后的回调接口
 * Created by xuning on 17/9/3.
 */
public interface DisposeDataListener<T> {

    /**
     * 请求成功
     * @param responseObj
     */
    void onSuccess(T responseObj);


    /**
     * 请求失败
     * @param responseObj
     */
    void onFail(Object responseObj);

}
