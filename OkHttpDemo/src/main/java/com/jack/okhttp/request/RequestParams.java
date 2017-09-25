package com.jack.okhttp.request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求参数
 * Created by xuning on 17/9/3.
 */

public class RequestParams {

    public ConcurrentHashMap<String, String> urlParams =
            new ConcurrentHashMap<String, String>();
    public ConcurrentHashMap<String, Object> fileParams =
            new ConcurrentHashMap<String, Object>();


    public RequestParams(){
        this((Map<String, String>)null);
    }

    public RequestParams(Map<String, String> source){
        if(source != null){
            for(Map.Entry<String, String> entry :source.entrySet()){
                urlParams.put(entry.getKey(), entry.getValue());
            }
        }

    }


}
