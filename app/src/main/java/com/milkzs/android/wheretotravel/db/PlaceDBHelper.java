package com.milkzs.android.wheretotravel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by milkdz on 2018/5/1.
 */

public class PlaceDBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "scenic_spot.db";
    private final static int version = 7;

    public PlaceDBHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable());
    }

    /**
     * Create table by sql lite
     *
     * @return
     */
    private String createTable(){
        return "CREATE TABLE " + PlaceContract.PlaceBase.TABLE_NAME + " ( "
                + PlaceContract.PlaceBase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + PlaceContract.PlaceBase.COLUMN_PLACE_ID + ","
                + PlaceContract.PlaceBase.COLUMN_PLACE_TIME + ","
                + PlaceContract.PlaceBase.COLUMN_PLACE_NAME + ","
                + PlaceContract.PlaceBase.COLUMN_PLACE_TIME_GO
                + " ); ";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PlaceContract.PlaceBase.TABLE_NAME);
        onCreate(db);
    }
}
