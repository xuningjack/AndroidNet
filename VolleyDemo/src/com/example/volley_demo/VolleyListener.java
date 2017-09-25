package com.example.volley_demo;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;


/**
 * 自定义的请求监听
 * Created by xuning on 17/5/31.
 */
public abstract class VolleyListener {

    private Context mContext;
    public static Response.Listener<String> mListener;
    public static Response.ErrorListener mErrorListener;

    public  VolleyListener(Context context,
                          Response.Listener<String> listener,
                          Response.ErrorListener errorListener){
        mContext = context;
        mListener = listener;
        mErrorListener = errorListener;
    }

    public Response.Listener<String> loadingListener(){
        mListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onMySuccess(response);
            }
        };
        return mListener;
    }

    public Response.ErrorListener errorListener(){
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onMyError(error);
            }
        };
        return mErrorListener;
    }

    public abstract void onMySuccess(String response);
    public abstract void onMyError(VolleyError error);

}
