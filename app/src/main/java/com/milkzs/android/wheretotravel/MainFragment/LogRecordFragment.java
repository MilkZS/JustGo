package com.milkzs.android.wheretotravel.MainFragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.milkzs.android.wheretotravel.Log.EditCustomDialog;
import com.milkzs.android.wheretotravel.MainFragment.adapter.LogListAdapter;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.db.PlaceContract;
import com.milkzs.android.wheretotravel.db.base.DBSQList;

public class LogRecordFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private LogListAdapter logListAdapter;

    public LogRecordFragment() {
    }

    public static LogRecordFragment newInstance() {
        return new LogRecordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_log_record, container, false);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_fragment_log);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        logListAdapter = new LogListAdapter();
        recyclerView.setAdapter(logListAdapter);
        getLoaderManager().initLoader(0,null,this);

        ImageView addImageView = view.findViewById(R.id.fragment_log_record_add_img);
        addImageView.setOnClickListener(new View.OnClickListener() {

            private EditCustomDialog editCustomDialog;

            @Override
            public void onClick(View v) {
                editCustomDialog = new EditCustomDialog();
                editCustomDialog.show(getFragmentManager(),"EditCustomDialog");
                editCustomDialog.setEditCustomDialogClickListener(editCustomDialogClickListener);
            }

            private EditCustomDialog.EditCustomDialogClickListener editCustomDialogClickListener = new EditCustomDialog.EditCustomDialogClickListener() {
                @Override
                public void clickImg() {

                }

                @Override
                public void clickButEnter(String sName, String sArriveTime, String sLeaveTime) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(PlaceContract.PlaceBase.COLUMN_PLACE_NAME,sName);
                    contentValues.put(PlaceContract.PlaceBase.COLUMN_PLACE_TIME,sArriveTime);
                    contentValues.put(PlaceContract.PlaceBase.COLUMN_PLACE_TIME_GO,sLeaveTime);
                    contentValues.put(PlaceContract.PlaceBase.COLUMN_PLACE_PIC,"");
                    Uri uri = PlaceContract.PlaceBase.CONTENT_BASE;
                    getContext().getContentResolver().bulkInsert(uri,new ContentValues[]{contentValues});
                    getLoaderManager().restartLoader(0,null,LogRecordFragment.this);
                }
            };
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = PlaceContract.PlaceBase.CONTENT_BASE;
        String order = PlaceContract.PlaceBase._ID + DBSQList.ORDER_BY_DESC;
        return new CursorLoader(
                getContext(), uri, PlaceContract.PlaceBase.QUERY_ENTRY, null, null, order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        logListAdapter.swap(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        logListAdapter.swap(null);
    }
}
