package com.example.david.webviewdemo;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;



/**
 * Created by xuning on 17/8/3.
 */
public class ShowJS {

    private Context mContext;

    public ShowJS(Context context){
        mContext = context;
    }

    @JavascriptInterface
    public void callJs(){
        Toast.makeText(mContext, "call js", Toast.LENGTH_LONG).show();
    }
}
