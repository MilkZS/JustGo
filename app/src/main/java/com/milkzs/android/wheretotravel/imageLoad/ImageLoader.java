package com.milkzs.android.wheretotravel.imageLoad;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.LruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by alan on 2018/8/2.
 */

public class ImageLoader {

    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDirskCache;


    public ImageLoader(Context context, String PathName) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        File file = getDiskCacheDir(context.getApplicationContext(), PathName);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            mDirskCache = DiskLruCache.open(file, getApplicationVersion(context), 1, cacheMemory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add pictures to cache.
     *
     * @param key    picture utl string
     * @param bitmap picture bitmap
     */
    public void addBitMapToCacheMemory(String key, Bitmap bitmap) {
        if (mMemoryCache.get(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * Add picture to DiskCache and this method start a new thread to do it.
     * @param urlstring string of picture string
     * @param bitmap bitmap of picture
     */
    public void addBitMapToDisMemory(String urlstring, Bitmap bitmap) {
        final String sp = urlstring;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String key = hashKeyForDisk(sp);
                if (mDirskCache != null) {
                    try {
                        DiskLruCache.Editor editor = mDirskCache.edit(key);
                        if (editor != null) {
                            OutputStream os = editor.newOutputStream(0);
                            if (downLoadUrlToStream(key, os)) {
                                editor.commit();
                            } else {
                                editor.abort();
                            }
                        }
                        mDirskCache.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Get file dir and first judge whether the device has external SD card.
     * If has return External storage dir {/sdcard/Android/data/<application package>/cache}
     * Or return cache dir. {/data/data/<application package>/cache}
     *
     * @param content  Application context
     * @param filename storage path
     * @return File
     */
    private File getDiskCacheDir(Context content, String filename) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = content.getExternalCacheDir().getPath();
        } else {
            cachePath = content.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + filename);
    }

    /**
     * Get app version and default is 1
     *
     * @param context Application context
     * @return version number
     */
    private int getApplicationVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * DownLoad picture into local file
     *
     * @param urlString URL of picture web address
     * @param os
     * @return success or fail
     */
    private boolean downLoadUrlToStream(String urlString, OutputStream os) {
        HttpURLConnection connection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(connection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(os, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Add MD5 secret for URL exclude repeat
     *
     * @param key URL of picture
     * @return MD5 URL string
     */
    private String hashKeyForDisk(String key) {
        String cachekey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cachekey = byteToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cachekey = String.valueOf(key.hashCode());
        }
        return cachekey;
    }

    private String byteToHexString(byte[] bs) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < bs.length; i++) {
            String hex = Integer.toHexString(0xFF & bs[i]);
            if (hex.length() == 1) s.append('0');
            s.append(hex);
        }
        return s.toString();
    }
}