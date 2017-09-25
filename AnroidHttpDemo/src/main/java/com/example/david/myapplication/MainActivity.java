package com.example.david.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mHttpGet, mHttpPost, mUrlConnectionGet,
            mUrlConnectionPost, mResult;
    private final String URL = "http://www.baidu.com";
    private final String LOC_URL = "http://v.juhe.cn/exp/index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHttpGet = (TextView)findViewById(R.id.httpClientGet);
        mHttpPost = (TextView)findViewById(R.id.httpClientPost);
        mUrlConnectionGet = (TextView)findViewById(R.id.urlConnectionGet);
        mUrlConnectionPost = (TextView)findViewById(R.id.urlConnectionPost);
        mResult = (TextView)findViewById(R.id.result);

        initListener();
    }


    private void initListener(){
        mHttpGet.setOnClickListener(this);
        mHttpPost.setOnClickListener(this);
        mUrlConnectionGet.setOnClickListener(this);
        mUrlConnectionPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.httpClientGet:
                new HttpClientGetTask().execute();
                break;
            case R.id.httpClientPost:
                new HttpClientPostTask().execute();
                break;
            case R.id.urlConnectionGet:
                new HttpUrlConnectionGetTask().execute();
                break;
            case R.id.urlConnectionPost:
                new HttpUrlConnectionPostTask().execute();
                break;
            default:
                break;
        }
    }

    /**
     * HttpClient执行Get请求
     */
    private class HttpClientGetTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            String result = httpClientGet();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!TextUtils.isEmpty(result)){
                mResult.setText(result);
            }
        }
    }

    /**
     * HttpUrlConnection执行Get请求
     */
    private class HttpUrlConnectionGetTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            String result = urlConnectionGet();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!TextUtils.isEmpty(result)){
                mResult.setText(result);
            }
        }
    }

    /**
     * HttpClient执行Post请求
     */
    private class HttpClientPostTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            String result = httpClientPost();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!TextUtils.isEmpty(result)){
                mResult.setText(result);
            }
        }
    }

    private class HttpUrlConnectionPostTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            String result = urlConnectionPost();
            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            if(!TextUtils.isEmpty(result)){
                mResult.setText(result);
            }
        }
    }




    /**
     * HttpClient执行get请求
     */
    private String httpClientGet(){
        HttpGet httpGet = new HttpGet(URL);
        HttpClient httpClient = new DefaultHttpClient();
        String result = "";
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(httpResponse != null &&
                httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            try {
                result = EntityUtils.toString(httpResponse.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            result = "get请求失败！";
        }
        return result;
    }

    /**
     * HttpUrlConnection执行get请求
     * @return
     */
    private String urlConnectionGet(){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(URL);
            HttpURLConnection httpURLConnection =
                    (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");  //get请求
            InputStreamReader isr = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bfr = new BufferedReader(isr);
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){  //请求成功
                String readLine = null;
                while((readLine = bfr.readLine()) != null){
                    stringBuilder.append(readLine);
                }
            }
            isr.close();
            bfr.close();
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * 执行post请求
     */
    private String httpClientPost() {
        HttpPost httpPost = new HttpPost(LOC_URL);
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", "123"));
        params.add(new BasicNameValuePair("com", "sf"));
        params.add(new BasicNameValuePair("no", "575677355677"));
        HttpEntity httpEntity = null;
        String result = "";

        try {
            httpEntity = new UrlEncodedFormEntity(params, "utf-8");
            httpPost.setEntity(httpEntity);
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            httpClient.setRedirectHandler(new RedirectHandler() {
                @Override
                public boolean isRedirectRequested(HttpResponse httpResponse,
                                                   HttpContext httpContext) {
                    return false;
                }

                @Override
                public URI getLocationURI(HttpResponse httpResponse,
                                          HttpContext httpContext)
                        throws ProtocolException {
                    return null;
                }
            });
            if(httpResponse != null &&
                    httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(httpResponse.getEntity());
            }else{
                result = "post请求失败！";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String urlConnectionPost(){
        String result = "";
        try {
            URL url = new URL(LOC_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection)
                    url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");

            String param = "key" + URLEncoder.encode("123", "UTF-8") + "&" +
            "com" + URLEncoder.encode("sf", "UTF-8") + "&" +
                    "no" + URLEncoder.encode("575677355677");

            httpURLConnection.connect();
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();


            //获得响应
            int resultCode = httpURLConnection.getResponseCode();
            if(HttpURLConnection.HTTP_OK == resultCode){
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                String readLine = null;
                while((readLine = bufferedReader.readLine()) != null){
                    stringBuilder.append(readLine);
                }
                result = stringBuilder.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
