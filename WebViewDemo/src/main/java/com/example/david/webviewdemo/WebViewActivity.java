package com.example.david.webviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebViewActivity extends Activity implements View.OnClickListener {

    private WebView mWebView;
    private TextView mTitle, mReload, mDownload, mLoadjs;
    private String mUrl = "https://www.hao123.com";
    private String mDownloadUrl = "http://www.25pp.com/android/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mTitle = (TextView) findViewById(R.id.title);
        mReload = (TextView) findViewById(R.id.reload);
        mDownload = (TextView) findViewById(R.id.downloadFile);
        mLoadjs = (TextView)findViewById(R.id.loadjs);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl(mUrl);   //只有这行会打开系统浏览器加载网页

        //需要重写shouldOverrideUrlLoading方法才能显示
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.endsWith("/gotoSecond")){   //调转第二个界面
                    Intent intent = new Intent(WebViewActivity.this, SecondActivity.class);
                    startActivity(intent);
                }else{   //显示网页
                    view.loadUrl(url);
                }
                return true;
            }

            //当加载页面出现错误时，显示本地的错误页面
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mWebView.loadUrl("file:///android_asset/error.html");
            }
        });

        //获取title并显示
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                mTitle.setText(title);
            }
        });

        //下载的监听，url是有效的apk的地址时触发
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition,
                                        String mimetype, long contentLength) {
                if(!TextUtils.isEmpty(url)){
                    if (url.endsWith(".apk")) {  //下载apk
                        HttpThread httpThread = new HttpThread(url);
                        httpThread.start();
                    }
                }
            }
        });


        mReload.setOnClickListener(this);
        mDownload.setOnClickListener(this);
        mLoadjs.setOnClickListener(this);

    }

    /**
     * 调用onClick对应的js代码
     */
    private void initJs(){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new ShowJS(this), "showJs");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reload:
                //刷新WebView
                mWebView.reload();
                break;
            case R.id.downloadFile:
                mWebView.loadUrl(mDownloadUrl);
                break;
            case R.id.loadjs:
                initJs();
                mWebView.loadUrl("file:///android_asset/showjs.html");
                break;
            default:
                break;
        }
    }

    /**
     * 网络请求下载文件
     */
    class HttpThread extends Thread {

        private String mUrl;
        private final String TAG = "HttpThread";

        public HttpThread(String mUrl) {
            this.mUrl = mUrl;
        }

        @Override
        public void run() {
            super.run();
            Log.d(TAG, "------start download");
            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL(mUrl);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {

                    InputStream is = null;
                    try {
                        is = httpURLConnection.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    File downloadFile;
                    File sdFile;
                    OutputStream ops = null;
                    //判断sd卡是否存在
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        downloadFile = Environment.getExternalStorageDirectory();
                        sdFile = new File(downloadFile, "test.apk");

                        try {
                            if (!sdFile.exists()) {
                                sdFile.createNewFile();
                            }
                            ops = new FileOutputStream(sdFile);
                            byte[] array = new byte[1024];
                            int length = 0;
                            while ((length = is.read(array)) != -1) {
                                ops.write(array, 0, length);
                            }
                            Log.d(TAG, "下载成功！");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (is != null) {
                                    is.close();
                                }
                                if (ops != null) {
                                    ops.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d(TAG, "------sucess download");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
