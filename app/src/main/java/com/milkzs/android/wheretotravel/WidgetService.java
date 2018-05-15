package com.milkzs.android.wheretotravel;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.milkzs.android.wheretotravel.db.PlaceContract;

/**
 * Created by alan on 2018/5/14.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.e("WidgetService","here");
        return new RemoteViewFactory(this.getApplicationContext());
    }
}

class RemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private String TAG = "RemoteViewFactory";

    private Cursor cursor;
    private Context context;

    public RemoteViewFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.e(TAG,"onDataSetChanged");
        if (cursor != null) {
            cursor.close();
        }

        Uri uri = PlaceContract.PlaceBase.CONTENT_BASE;
        String order = PlaceContract.PlaceBase.COLUMN_PLACE_TIME + " ASC";
        cursor = context.getContentResolver().query(
                uri,
                PlaceContract.PlaceBase.QUERY_ENTRY,
                null, null,
                order);
        Log.e(TAG,"onDataSetChanged cursor length is " + cursor.getCount());
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        if (cursor == null) {
            Log.e(TAG, "getCount cursor is null");
            return 0;
        }
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.e(TAG,"getViewAt");
        if (cursor == null) {
            return null;
        }
        cursor.moveToPosition(position);
        Log.e(TAG,"getViewAt next");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.log_list_app_widget);
        views.setTextViewText(
                R.id.appwidget_text,
                cursor.getString(
                        cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_TIME)));
        views.setTextViewText(
                R.id.appwidget_text_right,
                cursor.getString(
                        cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_NAME)));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

