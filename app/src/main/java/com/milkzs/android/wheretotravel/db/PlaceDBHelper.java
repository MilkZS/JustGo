package com.milkzs.android.wheretotravel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by milkdz on 2018/5/1.
 */

public class PlaceDBHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "scenic_spot.db";
    public final static int version = 8;

    public PlaceDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable());
        db.execSQL(createTableForScene());
        db.execSQL(createTableForSceneImg());
    }

    /**
     * Create table by sql lite
     *
     * @return
     */
    private String createTable() {
        return "CREATE TABLE " + PlaceContract.PlaceBase.TABLE_NAME + " ( "
                + PlaceContract.PlaceBase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + PlaceContract.PlaceBase.COLUMN_PLACE_PIC + ","
                + PlaceContract.PlaceBase.COLUMN_PLACE_ID + ","
                + PlaceContract.PlaceBase.COLUMN_PLACE_TIME + ","
                + PlaceContract.PlaceBase.COLUMN_PLACE_NAME + ","
                + PlaceContract.PlaceBase.COLUMN_PLACE_TIME_GO
                + " ); ";
    }

    private String createTableForScene(){
        return "CREATE TABLE " + PlaceContract.SceneBase.TABLE_NAME + " ( "
                + PlaceContract.SceneBase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + PlaceContract.SceneBase.COLUMN_SCENE_ID + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_ADDRESS + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_ATTENTION + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_MAIN_PIC + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_LOG_TIME + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_LOCATION_LAT + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_LOCATION_LON + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_CONTENT + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_DISCOUNT + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_NAME + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_PRICE + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_SUMMERY + ","
                + PlaceContract.SceneBase.COLUMN_SCENE_OPEN_TIME
                + " ); ";
    }

    private String createTableForSceneImg(){
        return "CREATE TABLE " + PlaceContract.SceneImgBase.TABLE_NAME + " ( "
                + PlaceContract.SceneImgBase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + PlaceContract.SceneImgBase.COLUMN_SCENE_ID + ","
                + PlaceContract.SceneImgBase.COLUMN_SCENE_IMG_URI + ","
                + PlaceContract.SceneImgBase.COLUMN_SCENE_IMG_BIG_URL
                + " ); ";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PlaceContract.PlaceBase.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PlaceContract.SceneBase.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PlaceContract.SceneImgBase.TABLE_NAME);
        onCreate(db);
    }
}
