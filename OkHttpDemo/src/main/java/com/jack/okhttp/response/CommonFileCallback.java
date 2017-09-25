package com.jack.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import com.jack.okhttp.listener.DisposeDataHandler;
import com.jack.okhttp.listener.DisposeDataListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



/**
 * 文件操作的回调接口
 * Created by xuning on 17/9/9.
 */
public class CommonFileCallback implements Callback{

    private DisposeDataListener mListener;
    private Class<?> mClass;
    private Handler mHandler;


    public CommonFileCallback(DisposeDataHandler mDelieverHandler) {
        mHandler = new Handler(Looper.getMainLooper());
        mListener = mDelieverHandler.mListener;
        mClass = mDelieverHandler.mClass;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
