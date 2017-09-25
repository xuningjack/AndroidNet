## Volley ##
Volley框架代码的学习，相关代码添加了中文注释，有利于代码阅读
官方git地址：https://android.googlesource.com/platform/frameworks/volley 
Volley提供的功能:
简单来说，它提供了如下的便利功能：
JSON，图像等的异步下载；
网络请求的排序（scheduling）;
网络请求的优先级处理;
缓存;
多级别取消请求;
和Activity和生命周期的联动（Activity结束时同时取消所有网络请求）.
json请求：

     mQueue = Volley.newRequestQueue(getApplicationContext());

    mQueue.add(new JsonObjectRequest(
        Method.GET, url, null,new Listener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "response : " + response.toString());
                }
            }, null));
    mQueue.start();  

获取网络图片：

// imageView是一个ImageView实例

// ImageLoader.getImageListener的第二个参数是默认的图片resource id
// 第三个参数是请求失败时候的资源id，可以指定为0

     ImageListener listener = ImageLoader.getImageListener(
     imageView,android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
    mImageLoader.get(url, listener);

或者：

    mImageView.setImageUrl(url, imageLoader)

Volley源码分析：
在RequestQueue类里面有这么一块代码：
  //可以传入一个自定义的HttpStack,比如OkHttpClient
  

      if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

代码中网络请求的实现由两种类型：
 一种是Java原生的HttpURLConnection实现（HurlStack），一种是Apache的HttpClient实现（HttpClientStack），Volley会在android2.3以前使用HttpClient实现，在android2.3及以后使用HttpURLConnection实现，至于原因，官方的解释是：在Eclair和Froyo上Apache HTTP client拥有更少的bug，更好的稳定性，在Gingerbread以及以后的版本中，HttpURLConnection是最好的选择，它简单的api以及轻量级非常适合Android。压缩和缓存机制降低了网路使用，提高了速度、节省了电量。
Volley优化

[添加支持GZIP的网络请求；添加进度显示；关于HTTPS][1]
    
[开放网络请求dispatcher线程数量；定义一个优化后的ImageLoader；添加一个返回字节数据的ImageRequest；给图片添加有效期；][2]
  [1]: https://www.zybuluo.com/flyouting/note/22485
  [2]: https://www.zybuluo.com/flyouting/note/21391
