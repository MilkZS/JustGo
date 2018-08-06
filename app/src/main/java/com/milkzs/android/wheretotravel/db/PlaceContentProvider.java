package com.milkzs.android.wheretotravel.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by milkdz on 2018/5/1.
 */

public class PlaceContentProvider extends ContentProvider {

    private String TAG = "PlaceContentProvider";
    private Boolean DBG = false;

    private final static int CODE_PLACE = 10;
    private final static int CODE_PLACE_ID = 11;

    private final static int CODE_SCENE = 20;
    private final static int CODE_SCENE_ID = 21;

    private final static int CODE_SCENE_PIC = 30;
    private final static int CODE_SCENE_PIC_ID = 31;

    private static UriMatcher uriMatcher = buildMatcher();

    private PlaceDBHelper placeDBHelper;
    private ScenePcturesDBHelperi scenePcturesDBHelperi;
    private SceneDBHelper sceneDBHelper;

    @Override
    public boolean onCreate() {
        placeDBHelper = new PlaceDBHelper(
                getContext(), PlaceDBHelper.DATABASE_NAME, null, PlaceDBHelper.version);
        scenePcturesDBHelperi = new ScenePcturesDBHelperi(getContext());
        sceneDBHelper = new SceneDBHelper(getContext());
        return true;
    }

    /**
     * query data from db by id or select all .
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CODE_PLACE: {
                cursor = getCursorForAll(PlaceContract.PlaceBase.TABLE_NAME,
                        PlaceContract.PlaceBase.QUERY_ENTRY, selection, selectionArgs, sortOrder,
                        placeDBHelper);
                Log.d(TAG, "provider cursor length is " + cursor.getCount());
            }
            break;
            case CODE_PLACE_ID: {
                String id = uri.getLastPathSegment();
                Log.d(TAG, "query by id and id is " + id);
                cursor = getCursorForId(
                        PlaceContract.PlaceBase.TABLE_NAME, projection,
                        PlaceContract.PlaceBase.COLUMN_PLACE_ID + "=48050",
                        placeDBHelper);
            }
            break;
            case CODE_SCENE:{
                cursor = getCursorForAll(PlaceContract.SceneBase.TABLE_NAME,
                        PlaceContract.SceneBase.QUERY_ENTRY, selection, selectionArgs, sortOrder,
                        sceneDBHelper);
            }break;
            case CODE_SCENE_ID:{
                cursor = getCursorForId(
                        PlaceContract.SceneBase.TABLE_NAME, projection,
                        PlaceContract.SceneBase.COLUMN_SCENE_ID+"="+uri.getLastPathSegment(),
                        sceneDBHelper);
            }break;
            case CODE_SCENE_PIC:{
                cursor = getCursorForAll(PlaceContract.SceneImgBase.TABLE_NAME,
                        PlaceContract.SceneImgBase.QUERY_ENTRY,selection,selectionArgs,sortOrder,
                        scenePcturesDBHelperi);
            }break;
            case CODE_SCENE_PIC_ID:{
                cursor = getCursorForId(PlaceContract.SceneImgBase.TABLE_NAME,projection,
                        PlaceContract.SceneImgBase.COLUMN_SCENE_ID + "=" + selection,
                        scenePcturesDBHelperi);
            }break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Get cursor for all data
     * @param tableName db table name
     * @param queryEntry query entry such as *
     * @param selection chose default null
     * @param selectionArgs chose values default null
     * @param sortOrder order by
     * @return cursor include result
     */
    private Cursor getCursorForAll(String tableName,String[] queryEntry,String selection,
                                   @Nullable String[] selectionArgs, @Nullable String sortOrder,
                                   SQLiteOpenHelper sqLiteOpenHelper){
        return sqLiteOpenHelper.getWritableDatabase().query(
                tableName,
                queryEntry,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    /**
     * Get cursor for data id
     * @param tableName db table name
     * @param projection query entry array
     * @param select chose sql string
     * @return cursor search by id
     */
    private Cursor getCursorForId(String tableName,String[] projection,String select,
                                  SQLiteOpenHelper sqLiteOpenHelper){
        return sqLiteOpenHelper.getWritableDatabase().query(
                tableName,projection,select,
                null,null,null,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        int rowCount = 0;
        switch (uriMatcher.match(uri)) {
            case CODE_PLACE: {
                rowCount = insertDataToDB(values,placeDBHelper,PlaceContract.PlaceBase.TABLE_NAME);
            }break;
            case CODE_SCENE:{
                rowCount = insertDataToDB(values,sceneDBHelper, PlaceContract.SceneBase.TABLE_NAME);
            }break;
            case CODE_SCENE_PIC:{
                rowCount = insertDataToDB(values,scenePcturesDBHelperi, PlaceContract.SceneImgBase.TABLE_NAME);
            }break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowCount > 0) {
            Toast.makeText(getContext(), "insert success ", Toast.LENGTH_SHORT).show();
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowCount;
    }

    /**
     * Insert data to DB
     * @param values content values of Data
     * @return rows insert successful
     */
    private int insertDataToDB(ContentValues[] values, SQLiteOpenHelper sqLiteOpenHelper,String tablename){
        int rowCount = -1;
        sqLiteOpenHelper.getWritableDatabase().beginTransaction();
        try {
            for (ContentValues c : values) {
                long id = sqLiteOpenHelper.getWritableDatabase().insert(
                        tablename, null, c);
                if (id != -1) {
                    rowCount++;
                }
            }
            sqLiteOpenHelper.getWritableDatabase().setTransactionSuccessful();
        } finally {
            sqLiteOpenHelper.getWritableDatabase().endTransaction();
        }
        return rowCount;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowid = 0;
        if (null == selection) {
            selection = "1";
        }
        switch (uriMatcher.match(uri)) {
            case CODE_PLACE: {
                rowid = placeDBHelper.getWritableDatabase().delete(
                        PlaceContract.PlaceBase.TABLE_NAME, selection, selectionArgs);
            }
            break;
            case CODE_SCENE:{
                rowid = sceneDBHelper.getWritableDatabase().delete(
                        PlaceContract.SceneBase.TABLE_NAME,
                        selection,selectionArgs
                );
            }break;
            case CODE_SCENE_PIC:{
                rowid = scenePcturesDBHelperi.getWritableDatabase().delete(
                        PlaceContract.SceneImgBase.TABLE_NAME,selection,selectionArgs);
            }break;
            default:
                throw new UnsupportedOperationException("Un know uri :" + uri);
        }

        if (rowid != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowid;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = -1;
        switch (uriMatcher.match(uri)) {
            case CODE_PLACE_ID: {
                String id = uri.getLastPathSegment();
                Log.d(TAG, "update id is " + id);
                count = placeDBHelper.getWritableDatabase().update(
                        PlaceContract.PlaceBase.TABLE_NAME,
                        values,
                        PlaceContract.PlaceBase.COLUMN_PLACE_ID + "=" + id,
                        null);
                if (DBG) Toast.makeText(getContext(), "here" + count, Toast.LENGTH_SHORT).show();
                if (count != -1) {
                    if (DBG)
                        Toast.makeText(getContext(), "update success", Toast.LENGTH_SHORT).show();
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;
            }
            default:
                throw new UnsupportedOperationException("Un know uri :" + uri);
        }
    }

    @Override
    public void shutdown() {
        placeDBHelper.close();
        sceneDBHelper.close();
        scenePcturesDBHelperi.close();
        super.shutdown();
    }

    /**
     * build uri matcher
     *
     * @return
     */
    private static UriMatcher buildMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String base = PlaceContract.CONTENT_AUTHORITY;
        matcher.addURI(base, PlaceContract.PlaceBase.PLACE_INFO, CODE_PLACE);
        matcher.addURI(base, PlaceContract.PlaceBase.PLACE_INFO + "/#", CODE_PLACE_ID);
        matcher.addURI(base,PlaceContract.SceneBase.SCENE_INFO,CODE_SCENE);
        matcher.addURI(base,PlaceContract.SceneBase.SCENE_INFO + "/#",CODE_SCENE_ID);
        matcher.addURI(base,PlaceContract.SceneImgBase.SCENEIMG_INFO,CODE_SCENE_PIC);
        matcher.addURI(base,PlaceContract.SceneImgBase.SCENEIMG_INFO + "/#",CODE_SCENE_PIC_ID);
        return matcher;
    }
}
