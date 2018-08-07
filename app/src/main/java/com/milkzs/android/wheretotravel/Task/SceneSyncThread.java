package com.milkzs.android.wheretotravel.Task;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.milkzs.android.wheretotravel.db.PlaceContract;

/**
 * Created by alan on 2018/8/7.
 */

public class SceneSyncThread {

    private static boolean ifFirst = true;

    public synchronized static void initialize(final Context context){
        if (!ifFirst){
            return;
        }
        ifFirst = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uri = PlaceContract.SceneBase.CONTENT_BASE;
                String[] projection = new String[]{PlaceContract.SceneBase.COLUMN_SCENE_ID};
                Cursor cursor = context.getContentResolver().query(
                        uri,projection,null,null,null);
                if(null == cursor || cursor.getCount() == 0){
                    Intent service = new Intent(context,SceneService.class);
                    context.startService(service);
                }
                if(cursor != null){
                    cursor.close();
                }
            }
        }).start();
    }
}
