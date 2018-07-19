package com.milkzs.android.wheretotravel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by milkdz on 2018/5/1.
 */

public class PlaceDBHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "scenic_spot.db";
    public final static int version = 7;

    public static final String SEARCH_TAG_DB_NAME = "search_tag.db";
    public static final int SEARCH_TAG_DB_VERSION = 1;

    private String db_name = "";

    public PlaceDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db_name = name;
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
    private String createTable() {

        if (db_name.equals(SEARCH_TAG_DB_NAME)) {
            return "CREATE TABLE " + PlaceContract.SearchTAG.TABLE_NAME + " ( "
                    + PlaceContract.SearchTAG._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + PlaceContract.SearchTAG.COLUMN_ID + ","
                    + PlaceContract.SearchTAG.COLUMN_TAG + ","
                    + PlaceContract.SearchTAG.COLUMN_USER_ID + ","
                    + " );";
        }

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
        db.execSQL("DROP TABLE IF EXISTS " + PlaceContract.SearchTAG.TABLE_NAME);
        onCreate(db);
    }
}
