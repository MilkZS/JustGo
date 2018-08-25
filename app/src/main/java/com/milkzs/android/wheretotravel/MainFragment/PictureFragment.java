package com.milkzs.android.wheretotravel.MainFragment;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.adapter.MorePicturesAdapter;
import com.milkzs.android.wheretotravel.db.PlaceContract;
import com.milkzs.android.wheretotravel.db.base.DBSQList;

public class PictureFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private String TAG = "PictureFragment";
    private MorePicturesAdapter morePicturesAdapter;

    public PictureFragment() {
    }


    public static PictureFragment newInstance() {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_picture, container, false);
        }
        RecyclerView recyclerView = view.findViewById(R.id.recycler_fragment_pic);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        morePicturesAdapter = new MorePicturesAdapter();
        recyclerView.setAdapter(morePicturesAdapter);

        getLoaderManager().initLoader(0,null,this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = PlaceContract.SceneImgBase.CONTENT_BASE;
        String order = PlaceContract.SceneImgBase.COLUMN_SCENE_ID+ DBSQList.ORDER_BY;
        return new CursorLoader(
                getContext(), uri, PlaceContract.SceneImgBase.QUERY_ENTRY, null, null, order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (morePicturesAdapter != null)
            Log.d(TAG,"cursor data length is " + data.getCount());
            morePicturesAdapter.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
