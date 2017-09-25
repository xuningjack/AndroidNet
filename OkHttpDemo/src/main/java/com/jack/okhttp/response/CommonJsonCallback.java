package com.jack.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.jack.okhttp.exception.OkHttpException;
import com.jack.okhttp.listener.DisposeDataHandler;
import com.jack.okhttp.listener.DisposeDataListener;
import com.jack.okhttp.utils.JsonHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * json响应回调
 * Created by xuning on 17/9/3.
 */
public class CommonJsonCallback<T> implements Callback{

    /**
     * TODO 服务器定义的协议，有返回对于http请求来说可能是成功的，但还可能是
     */
    protected final String RESULT_CODE = "encode";
    /**
     * 服务器返回异常
     */
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";

    /**
     * 网络相关的异常
     */
    protected final int NETWORK_ERROR = -1;
    /**
     * json相关的异常
     */
    protected final int JSON_ERROR = -2;
    /**
     * 其他异常
     */
    protected final int OTHER_ERROR = -3;


    private DisposeDataListener mListener;
    private Class<?> mClass;
    private Handler mHandler;




    public CommonJsonCallback(DisposeDataHandler disposeDataHandler){
        mListener = disposeDataHandler.mListener;
        mClass = disposeDataHandler.mClass;
        mHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void onFailure(Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFail(e);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        //在子线程中的操作
        final String result = response.body().string();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(String result) {
        if(TextUtils.isEmpty(result)){
            mListener.onFail(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }else{
            try {
                JSONObject resultObj = new JSONObject(result);
                if(resultObj.has(RESULT_CODE)){   //有encode
                    if(resultObj.optInt(RESULT_CODE) == RESULT_CODE_VALUE){
                        if(mClass == null){
                            mListener.onSuccess(resultObj);
                        }else{   //TODO 开始解析
                            Object obj = JsonHelper.getResult(result, Object.class);
                            if(obj == null){
                                mListener.onFail(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                            }else{  //这里才开始真正处理响应，返回实体对象
                                mListener.onSuccess(obj);
                            }
                        }
                    }else{  //服务器返回异常
                        mListener.onFail(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                    }
                }else{  //没有encode
                    if(mClass == null){
                        mListener.onSuccess(resultObj);
                    }else{   //TODO 开始解析
                        Object obj = JsonHelper.getResult(result, Object.class);
                        if(obj == null){
                            mListener.onFail(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }else{  //这里才开始真正处理响应，返回实体对象
                            mListener.onSuccess(obj);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mListener.onFail(new OkHttpException(OTHER_ERROR, e.getMessage()));
            }


        }

    }
}
