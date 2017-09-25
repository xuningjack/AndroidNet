package com.example.jack.okhttpdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jack.okhttp.CommonOkHttpClient;
import com.jack.okhttp.listener.DisposeDataHandler;
import com.jack.okhttp.listener.DisposeDataListener;
import com.jack.okhttp.listener.DownloadResponseHandler;
import com.jack.okhttp.request.CommonRequest;
import com.jack.okhttp.request.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;







/**
 * 原始okhttp使用
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private final String TAG = "MainActivity";
    private final String URL = "http://www.sojson.com/api/beian/www.baidu.com";
    private final String CITY_URL = Constants.CITY_URL;
    private final String IMAGE_URL = "http://images.csdn.net/20150817/1.jpg";
    private Button mGet, mPost, mDownload, mUpload;
    private ImageView mImageView;
    private TextView mResult;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.get:
                getRequest();
                break;
            case R.id.post:
                //postRequest();
                customOkhttpPost();
                break;
            case R.id.download:
                downloadFile();
                break;
            case R.id.upload:
                uploadFile();
                break;
            default:
                break;
        }
    }


    /**
     * 下载文件
     */
    public void downloadFile(){
//        if(hasPermission(Constants.WRITE_READ_EXTERNAL_PERMISSION)){
//            doSDCardPermission();
//        }else{
//            requestPermissions(Constants.WRITE_READ_EXTERNAL_CODE, Constants.WRITE_READ_EXTERNAL_PERMISSION);
//        }
        Context context = MainActivity.this;
        String url = IMAGE_URL;
        final String filedir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        final String filename = System.currentTimeMillis() + ".jpg";

        Log.d(TAG, "before path-------" + filedir + "\t" + filename);
        DownloadResponseHandler downloadResponseHandler = new DownloadResponseHandler() {
            @Override
            public void onFinish(File downloadFile) {
                Log.d(TAG, "--------download success");
                String path = filedir + filename;
                Log.d(TAG, "path-------" + path);
                Bitmap bitmap = BitmapFactory.decodeFile(filedir + filename);
                if(bitmap != null){
                    mImageView.setImageBitmap(bitmap);
                }else{
                    Log.e(TAG, "download file -------- not exist");
                }


            }

            @Override
            public void onProgress(long currentBytes, long totalBytes) {
                Log.d(TAG, "--------download " + (currentBytes * 100 / totalBytes) + "%");
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.d(TAG, "--------download fail " + errorMsg);
            }
        };
        CommonOkHttpClient.downloadFile(context, url, filedir, filename, downloadResponseHandler);
    }

    /**
     * 上传文件
     */
    public void uploadFile(){
        RequestParams params = new RequestParams();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "test2.jpg";
        params.fileParams.put("test", new File(path));
        CommonOkHttpClient.post(CommonRequest.createMultiPostRequest("https://api.imgur.com/3/image", params),
                new DisposeDataHandler(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        Log.d(TAG, "upload file success!!!");
                    }

                    @Override
                    public void onFail(Object responseObj) {
                        Log.d(TAG, "upload file fail!!!");
                    }
                }));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGet = (Button)findViewById(R.id.get);
        mPost = (Button)findViewById(R.id.post);
        mResult = (TextView)findViewById(R.id.reslt);
        mDownload = (Button)findViewById(R.id.download);
        mUpload = (Button)findViewById(R.id.upload);
        mImageView = (ImageView)findViewById(R.id.imageView);

        mGet.setOnClickListener(this);
        mPost.setOnClickListener(this);
        mDownload.setOnClickListener(this);
        mUpload.setOnClickListener(this);
    }


    /**
     * 发送get请求
     */
    private void getRequest(){
        //initialOkhttpGet();
        customOkhttpGet();
    }

    private void initialOkhttpGet(){
        final Request request = new Request.Builder().url(URL).build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "get request fail!!!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response != null){   //非UI线程
                    final String result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mResult.setText(result);
                        }
                    });
                }
            }
        });
    }

    /**
     * get请求
     */
    private void customOkhttpGet(){
        Request request = CommonRequest.createGetRequest(URL, null);
        CommonOkHttpClient.get(request, new DisposeDataHandler(new DisposeDataListener<DnsBean>() {
            @Override
            public void onSuccess(DnsBean responseObj) {
                //final String result = responseObj.body().string();
                final String result = responseObj.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResult.setText("");
                        mResult.setText(result);
                    }
                });
            }

            @Override
            public void onFail(final Object reasonObj) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResult.setText(reasonObj.toString());
                    }
                });
            }
        }));
    }

    /**
     * 发送自定义请求
     */
    private void customOkhttpPost(){
        RequestParams params = new RequestParams();
        params.urlParams.put("name", "Jack");
        params.urlParams.put("pwd", "123456");
        CommonOkHttpClient.post(CommonRequest.createPostRequest(CITY_URL, params),
                new DisposeDataHandler(new DisposeDataListener<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject responseObj) {
                        mResult.setText(responseObj.toString());
                    }

                    @Override
                    public void onFail(Object responseObj) {
                        Log.e(TAG, "post onFail------" + responseObj.toString());
                    }
                }));
    }


    /**
     * 发送post请求
     */
    private void postRequest(){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("name", "Jack");
        builder.add("age", "30");
        Request request = new Request.Builder().url(CITY_URL).post(builder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "get request fail!!!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response != null){  //非UI线程
                    final String result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mResult.setText(result);
                        }
                    });
                }
            }
        });
    }


}
