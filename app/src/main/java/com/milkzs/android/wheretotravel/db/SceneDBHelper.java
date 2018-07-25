package com.milkzs.android.wheretotravel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alan on 2018/7/25.
 */

public class SceneDBHelper extends SQLiteOpenHelper {

    private final static int version = 1;
    private final  static String DB_NAME = "scene_info.db";

    public SceneDBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PlaceContract.SceneBase.TABLE_NAME + " ( "
                + PlaceContract.SceneBase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + PlaceContract.SceneBase.COLUMN_SCENE_ID + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_ADDRESS + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_ATTENTION + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_BIG_PIC + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_LOG_TIME + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_LOCATION_LAT + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_LOCATION_LON + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_CONTENT + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_DISCOUNT + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_NAME + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_PRICE + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_SUMMERY
                + " ); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PlaceContract.SceneBase.TABLE_NAME);
    }
}
