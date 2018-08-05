package com.milkzs.android.wheretotravel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alan on 2018/8/1.
 */

public class ScenePcturesDBHelperi extends SQLiteOpenHelper {

    private final static String DB_NAME = "scene_picture.db";
    private final static int VERSION = 1;

    public ScenePcturesDBHelperi(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PlaceContract.SceneImgBase.TABLE_NAME + " ( "
                + PlaceContract.SceneImgBase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + PlaceContract.SceneImgBase.COLUMN_SCENE_ID + ","
                + PlaceContract.SceneImgBase.COLUMN_SCENE_IMG_URI + ","
                + PlaceContract.SceneImgBase.COLUMN_SCENE_IMG_BIG_URL
                + " ); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PlaceContract.SceneImgBase.TABLE_NAME);
    }
}
