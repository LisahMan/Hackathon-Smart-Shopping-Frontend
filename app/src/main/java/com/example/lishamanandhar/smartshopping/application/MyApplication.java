package com.example.lishamanandhar.smartshopping.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by LishaManandhar on 3/8/2018.
 */

public class MyApplication extends Application {

    private RequestQueue mRequestQueue;
    private static MyApplication mInstance;
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue(){

        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public static MyApplication getInstance(){
        return mInstance;
    }

    public ImageLoader getImageLoader(){
        getRequestQueue();
        if(mImageLoader==null){
            mImageLoader = new ImageLoader(this.mRequestQueue,new LruBitmapCache());
        }
        return this.mImageLoader;
    }
}
