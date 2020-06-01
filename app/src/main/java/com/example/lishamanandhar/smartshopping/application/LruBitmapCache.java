package com.example.lishamanandhar.smartshopping.application;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by LishaManandhar on 3/8/2018.
 */

public class LruBitmapCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {

    public static int getDefaultSizeCacheSize(){

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory/8;
        return cacheSize;
    }

    public LruBitmapCache(){
        this(getDefaultSizeCacheSize());
    }

    public LruBitmapCache(int sizeInKiloBytes){
        super(sizeInKiloBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight()/1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url,bitmap);
    }
}
