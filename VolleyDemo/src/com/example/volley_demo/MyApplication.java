package com.example.volley_demo;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;




/**
 * 自定义application
 */
public class MyApplication extends Application {
	
	public static RequestQueue queues;
	
	@Override
	public void onCreate() {
		super.onCreate();
		queues = Volley.newRequestQueue(getApplicationContext());
	}
	
	
	public static RequestQueue getHttpRequestQueue(){
		return queues;
	}
}
