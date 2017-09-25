package com.example.volley_demo;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;




/**
 * 自己封装get和post请求
 */
public class CustomVolleyRequest {

    public static StringRequest mStringRequest;


    /**
     * 执行get请求
     * @param url
     * @param tag
     * @param listener
     */
    public static void requestGet(String url, String tag, VolleyListener listener){
        MyApplication.getHttpRequestQueue().cancelAll(tag);
        mStringRequest = new StringRequest(Request.Method.GET, url,
                listener.loadingListener(), listener.errorListener());
        mStringRequest.setTag(tag);
        MyApplication.getHttpRequestQueue().add(mStringRequest);
        MyApplication.getHttpRequestQueue().start();
    }


    /**
     * 执行post请求
     * @param url
     * @param tag
     * @param params
     * @param listener
     */
    public static void requestPost(String url, String tag,
                                   final Map<String, String> params, VolleyListener listener){
        MyApplication.getHttpRequestQueue().cancelAll(tag);
        mStringRequest = new StringRequest(Request.Method.POST, url,
                listener.loadingListener(), listener.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        mStringRequest.setTag(tag);
        MyApplication.getHttpRequestQueue().add(mStringRequest);
        MyApplication.getHttpRequestQueue().start();

    }

}
