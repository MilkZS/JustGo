package com.milkzs.android.wheretotravel.imageLoad;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.io.File;
import java.io.IOException;


/**
 * Created by alan on 2018/8/2.
 */

public class ImageLoader  {

    private LruCache<String,Bitmap> mMemoryCache;
    private DiskLruCache mDirskCache;


    public ImageLoader(){
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory/8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };

        File file = new File("Scene_pic");
        if(!file.exists()){
            file.mkdir();
        }
        try {
            mDirskCache = DiskLruCache.open(file,1,1,cacheMemory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBitMapToMemory(String key,Bitmap bitmap){
        if(mDirskCache != null){
            //final DiskLruCache.Editor editor = mDirskCache.Editor(key);
        }
    }
}
