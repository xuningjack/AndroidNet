package com.example.volley_demo;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;



/**
 * 自定义图片缓存
 * Created by xuning on 17/8/13.
 */
public class BitmapCache implements ImageLoader.ImageCache {

    public LruCache<String, Bitmap> mCache;
    public final int MAX = 10 * 1024;

    public BitmapCache(){
        mCache = new LruCache<String, Bitmap>(MAX){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }
}
