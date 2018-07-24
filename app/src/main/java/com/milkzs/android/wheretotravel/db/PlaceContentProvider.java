package com.milkzs.android.wheretotravel.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
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

    private static UriMatcher uriMatcher = buildMatcher();

    private PlaceDBHelper placeDBHelper;

    @Override
    public boolean onCreate() {
        placeDBHelper = new PlaceDBHelper(
                getContext(), PlaceDBHelper.DATABASE_NAME, null, PlaceDBHelper.version);
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
                cursor = placeDBHelper.getReadableDatabase().query(
                        PlaceContract.PlaceBase.TABLE_NAME,
                        PlaceContract.PlaceBase.QUERY_ENTRY,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                Log.d(TAG, "provider cursor length is " + cursor.getCount());
            }
            break;
            case CODE_PLACE_ID: {
                String id = uri.getLastPathSegment();
                Log.d(TAG, "query by id and id is " + id);
                cursor = placeDBHelper.getWritableDatabase().query(
                        PlaceContract.PlaceBase.TABLE_NAME,
                        projection,
                        PlaceContract.PlaceBase.COLUMN_PLACE_ID + "=48050",
                        null,
                        null,
                        null,
                        null);
            }
            break;
            case CODE_SCENE:{
                cursor = placeDBHelper.getReadableDatabase().query(
                        PlaceContract.SceneBase.TABLE_NAME,
                        PlaceContract.SceneBase.QUERY_ENTRY,
                        selection,
                        selectionArgs,
                        null,null,
                        sortOrder);
            }break;
            case CODE_SCENE_ID:{
                cursor = placeDBHelper.getReadableDatabase().query(
                        PlaceContract.SceneBase.TABLE_NAME,
                        projection,
                        PlaceContract.SceneBase.COLUMN_SCENE_ID+"="+uri.getLastPathSegment(),
                        null,
                        null,
                        null,
                        null
                );
            }break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
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
                placeDBHelper.getWritableDatabase().beginTransaction();
                try {
                    for (ContentValues c : values) {
                        long id = placeDBHelper.getWritableDatabase().insert(
                                PlaceContract.PlaceBase.TABLE_NAME, null, c);
                        if (id != -1) {
                            rowCount++;
                        }
                    }

                    placeDBHelper.getWritableDatabase().setTransactionSuccessful();
                } finally {
                    placeDBHelper.getWritableDatabase().endTransaction();
                }
            }
            case CODE_SCENE:{
                placeDBHelper.getWritableDatabase().beginTransaction();
                try{
                    for (ContentValues contentValues:values) {
                        long id = placeDBHelper.getWritableDatabase().insert(
                                PlaceContract.SceneBase.TABLE_NAME,
                                null,
                                contentValues
                        );
                        if(id != -1) rowCount ++;
                    }
                    placeDBHelper.getWritableDatabase().setTransactionSuccessful();
                }finally {
                    placeDBHelper.getWritableDatabase().endTransaction();
                }
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
                rowid = placeDBHelper.getWritableDatabase().delete(
                        PlaceContract.SceneBase.TABLE_NAME,
                        selection,selectionArgs
                );
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
        return matcher;
    }
}
