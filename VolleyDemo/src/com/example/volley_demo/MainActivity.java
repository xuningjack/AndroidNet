package com.example.volley_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.example.volley_demo.Constants.URL;


public class MainActivity extends Activity implements View.OnClickListener{

	private final String TAG = "MainActivity";
	private String url = URL;
	private Response.ErrorListener errorListener = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {   //数据请求失败返回
			Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.gotoimg:
				startActivity(new Intent(MainActivity.this, ImageActivity.class));
				break;
			case R.id.volleyStringGet:
				volleyStringGet();
				break;
			case R.id.volleyJsonObjectGet:
				volleyJsonObjectGet();
				break;
			case R.id.volleyCustomGet:
				volleyCustomGet();
				break;
			case R.id.volleyStringPost:
				volleyStringPost();
				break;
			case R.id.volleyJsonObjectPost:
				volleyJsonObjectPost();
				break;
			default:
				break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.gotoimg).setOnClickListener(this);
		findViewById(R.id.volleyStringGet).setOnClickListener(this);
		findViewById(R.id.volleyJsonObjectGet).setOnClickListener(this);
		findViewById(R.id.volleyCustomGet).setOnClickListener(this);
		findViewById(R.id.volleyStringPost).setOnClickListener(this);
		findViewById(R.id.volleyJsonObjectPost).setOnClickListener(this);
	}


	/**
	 * get请求String
	 */
	private void volleyStringGet(){
		Response.Listener<String> listener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {   //数据请求成功返回
				Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
			}
		};
		StringRequest request = new StringRequest(Method.GET, url, listener, errorListener);
		request.setTag("jackStringRequest");
		MyApplication.getHttpRequestQueue().add(request);
	}

	/**
	 * get请求jsonobject
	 */
	private void volleyJsonObjectGet(){
		Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
				Log.d(TAG, "JSONObject-----------" + response.toString());
			}
		};
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, listener, errorListener);
		request.setTag("jackJsonObjectRequest");
		MyApplication.getHttpRequestQueue().add(request);
	}

	/**
	 * 使用自定义的get方式请求数据，二次封装
	 */
	private void volleyCustomGet(){
		CustomVolleyRequest.requestGet(url, "volleyCustomGet", new VolleyListener(this,
				VolleyListener.mListener, VolleyListener.mErrorListener) {
			@Override
			public void onMySuccess(String response) {
				Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
				Log.d(TAG, "volleyCustomGet-----" + response);
			}

			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
				Log.d(TAG, "volleyCustomGet error-----" + error.getMessage());
			}
		});
	}



	/**
	 * todo 服务器需要能够解析json参数
	 */
	private void volleyJsonObjectPost(){
		String url = "http://apis.juhe.cn/mobile/get?";
		Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){
			@Override
			public void onResponse(JSONObject response) {
				if(response != null){
					Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
					Log.d(TAG, "volleyJsonObjectPost----" + response.toString());
				}
			}
		};

		final String requestBody = "phone=13811253688&key=cf365ff9aa9f1daddea8357b43267fd7";
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, null,
				listener, errorListener){

			@Override
			public byte[] getPostBody() {
				return getBody();
			}

			@Override
			public byte[] getBody() {
				try {
					return requestBody.toString().getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		request.setTag("jsonPost");
		MyApplication.getHttpRequestQueue().add(request);
	}


	/**
	 * post请求
	 */
	private void volleyStringPost() {
		String url = "http://apis.juhe.cn/mobile/get?";
		Response.Listener<String> listener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(response != null) {
					Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
					Log.d(TAG, "JSONObject-----------" + response.toString());
				}
			}
		};
		StringRequest request = new StringRequest(Method.POST, url, listener, errorListener){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("phone", "13811253688");
				hashMap.put("key", "cf365ff9aa9f1daddea8357b43267fd7");
				return hashMap;
			}
		};
		request.setTag("jackPost");
		MyApplication.getHttpRequestQueue().add(request);
	}

	@Override
	protected void onStop() {
		super.onStop();
		MyApplication.getHttpRequestQueue().cancelAll("jackStringRequest");
		MyApplication.getHttpRequestQueue().cancelAll("jackJsonObjectRequest");
		MyApplication.getHttpRequestQueue().cancelAll("jackPost");
		MyApplication.getHttpRequestQueue().cancelAll("jsonPost");

	}
}
