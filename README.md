# AndroidNet
本项目涵盖了JAVA网络、Android网络操作（网络请求、WebView相关、常用第三方框架OkHttp&amp;Volley）


## 1 JavaSocketDemo  
本demo演示了InetAddress、URL的用法，模拟单客户端、多客户端的socket和udp的通信。  

## 2 AndroidHttpDemo  
本demo演示了使用HttpClient、HttpUrlConnection发送网络请求。  

## 3 WebViewDemo 
本demo演示了js与java原生代码的交互，还有通过html与java原生代码的交互。  


## 4 VolleyDemo  
本demo演示了volley的常规使用和封装。  

Volley Module中封装了自定义的请求对象CustomVolleyRequest和请求回调VolleyListener。  
使用其封装get请求如下：
  /**  
	 * 使用自定义的get方式请求数据，二次封装  
	 */  
	private void volleyCustomGet(){   
		CustomVolleyRequest.requestGet(url, 
		"volleyCustomGet", 
		new VolleyListener(this, VolleyListener.mListener, VolleyListener.mErrorListener) {  
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


## 5 OkHttpDemo  
本demo演示了OkHttp的常规使用和封装。

