package com.jack.okhttp;

import android.content.Context;
import android.os.Handler;

import com.jack.okhttp.body.ResponseProgressBody;
import com.jack.okhttp.listener.DisposeDataHandler;
import com.jack.okhttp.listener.DownloadResponseHandler;
import com.jack.okhttp.listener.MyDownloadCallback;
import com.jack.okhttp.response.CommonFileCallback;
import com.jack.okhttp.response.CommonJsonCallback;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 用来发送get post请求的工具类，包括设置一些请求的共用参数
 * Created by xuning on 17/9/3.
 */
public class CommonOkHttpClient {

    private static final int TIME_OUT = 30;
    private static OkHttpClient sOkHttpClient;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;  // TODO: 17/9/4  https认证
            }
        });
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.followRedirects(true);
        //builder.sslSocketFactory(HttpsUtils.getSslSocketFactory());  //信任所有证书

        sOkHttpClient = builder.build();
    }

    /**
     * get请求
     * @param request
     * @param handler
     * @return
     */
    public static Call get(Request request, DisposeDataHandler handler) {
        Call call = sOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handler));
        return call;
    }


    /**
     * post请求
     * @param request
     * @param handler 只针对json的请求封装回调
     * @return
     */
    public static Call post(Request request, DisposeDataHandler handler) {
        Call call = sOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handler));
        return call;
    }

    /**
     * 下载请求
     * @param request
     * @param handler
     * @return
     */
    public static Call downloadFile(Request request, String file, DisposeDataHandler handler) {
        Call call = sOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(handler));
        return call;
    }


    /**
     * 下载文件
     * @param context 发起请求的context
     * @param url 下载地址
     * @param filedir 下载目的目录
     * @param filename 下载目的文件名
     * @param downloadResponseHandler 下载回调
     */
    public static void downloadFile(Context context, String url,
                                    String filedir, String filename,
                         final DownloadResponseHandler downloadResponseHandler) {

        Request request;
        if(context == null) {
            request = new Request.Builder()
                    .url(url)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .tag(context)
                    .build();
        }

        sOkHttpClient.newBuilder()
                .addNetworkInterceptor(new Interceptor() {      //设置拦截器
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ResponseProgressBody(originalResponse.body(),
                                        downloadResponseHandler))
                                .build();
                    }
                })
                .build()
                .newCall(request)
                .enqueue(new MyDownloadCallback(new Handler(),
                        downloadResponseHandler, filedir, filename));
    }


}
