package com.jack.okhttp.listener;

/**
 * Created by xuning on 17/9/3.
 */

public class DisposeDataHandler {

    public DisposeDataListener mListener;
    public Class<?> mClass;



    public DisposeDataHandler(DisposeDataListener mListener) {
        this.mListener = mListener;
    }

    public DisposeDataHandler(DisposeDataListener mListener, Class<?> mClass) {
        this.mListener = mListener;
        this.mClass = mClass;
    }
}
