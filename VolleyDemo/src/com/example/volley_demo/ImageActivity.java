package com.example.volley_demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;





/**
 * 网络图片加载的Activity
 * Created by xuning on 17/8/13.
 */
public class ImageActivity extends Activity {

    private ImageView mImageView;
    private Response.Listener mListener = new Response.Listener<Bitmap>() {
        @Override
        public void onResponse(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    };
    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(ImageActivity.this, "加载失败",
                    Toast.LENGTH_SHORT).show();
        }
    };
    private NetworkImageView mNetworkImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        mImageView = (ImageView)findViewById(R.id.imageView);
        mNetworkImageView = (NetworkImageView)findViewById(R.id.networkImg);

        String url = "https://www.baidu.com/img/bdlogo.png";
        String netUrl = "https://gss1.bdstatic.com/5eN1dDebRNRTm2_p8IuM_a/res/img/richanglogo168_24.png";
        //loadImage(url);
        loadImageWithCache(url);


        loadNetworkImage(netUrl);
    }

    private void loadNetworkImage(String url){
        ImageLoader imageLoader = new ImageLoader(MyApplication.getHttpRequestQueue(), new BitmapCache());
        mNetworkImageView.setDefaultImageResId(R.drawable.ic_launcher);
        mNetworkImageView.setErrorImageResId(R.drawable.ic_launcher);
        mNetworkImageView.setImageUrl(url, imageLoader);
    }

    /**
     * 正常无缓存加载Image
     */
    private void loadImage(String url){
        ImageRequest imageRequest = new ImageRequest(url,
                mListener, 300, 300, Bitmap.Config.RGB_565, mErrorListener);
        MyApplication.getHttpRequestQueue().add(imageRequest);
    }

    /**
     * 增加缓存加载图片
     * @param url
     */
    private void loadImageWithCache(String url){
        ImageLoader imageLoader = new ImageLoader(MyApplication.getHttpRequestQueue(),
                new BitmapCache());
        ImageLoader.ImageListener listener = imageLoader.getImageListener(mImageView,
                R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(url, listener);

    }
}
