package com.milkzs.android.wheretotravel;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.Task.QueryDataTask;
import com.milkzs.android.wheretotravel.Titanic.TitanicTextView;
import com.milkzs.android.wheretotravel.adapter.PlaceAdapter;
import com.milkzs.android.wheretotravel.db.PlaceContract;


public class PlaceListActivity extends AppCompatActivity implements PlaceAdapter.ClickTranform {

    private String TAG = "PlaceListActivity";
    private boolean DBG = false;

    private PlaceAdapter placeAdapter;
    private RecyclerView recyclerView;

    private QueryDataTask queryDataTask;
    private TitanicTextView titanicTextView;

    private GridLayoutManager gridLayoutManager;
    private Toolbar mToolbar;

    private int position = 0;
    private String POSITION_FLAG = "flag_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        if(savedInstanceState != null){
            position = savedInstanceState.getInt(POSITION_FLAG);
        }

        mToolbar = findViewById(R.id.list_toolbar);
        setSupportActionBar(mToolbar);

        recyclerView = findViewById(R.id.main_recycler);

        gridLayoutManager = new GridLayoutManager(
                this, getResources().getInteger(R.integer.grid_layout_span_list));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(position);

        titanicTextView = findViewById(R.id.before_main_show);
        refreshMode(PlaceAdapter.MODE_LIST);
    }

    private void refreshMode(int mode) {
        switch (mode) {
            case PlaceAdapter.MODE_LIST: {

                gridLayoutManager.setSpanCount(
                        getResources().getInteger(R.integer.grid_layout_span_list));
                recyclerView.setLayoutManager(gridLayoutManager);
                placeAdapter = new PlaceAdapter(this, PlaceAdapter.MODE_LIST);
                recyclerView.setAdapter(placeAdapter);

                if (queryDataTask != null) {
                    queryDataTask.cancel(true);
                }
                queryDataTask = new QueryDataTask(this, placeAdapter, titanicTextView);
                queryDataTask.execute();
            }
            break;
            case PlaceAdapter.MODE_LOG: {

                gridLayoutManager.setSpanCount(1);
                recyclerView.setLayoutManager(gridLayoutManager);
                placeAdapter = new PlaceAdapter(this, PlaceAdapter.MODE_LOG);
                recyclerView.setAdapter(placeAdapter);

                if (queryDataTask != null) {
                    queryDataTask.cancel(true);
                }
                getSupportLoaderManager().initLoader(0, null, mCallback);
            }
            break;
        }
    }

    @Override
    public void onClick(int position, PlaceListInfo placeListInfo) {
        if (DBG) Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PlaceDetailActivity.class);
        intent.putExtra(BaseInfo.IntentFlag.FLAG_ARRAY_LIST_DETAIL, placeListInfo);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_list) {
            refreshMode(PlaceAdapter.MODE_LIST);
        } else if (item.getItemId() == R.id.item_log) {
            refreshMode(PlaceAdapter.MODE_LOG);
        }
        return true;
    }

    private final LoaderManager.LoaderCallbacks<Cursor> mCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Uri placeUri = PlaceContract.PlaceBase.CONTENT_BASE;
            String order = PlaceContract.PlaceBase.COLUMN_PLACE_TIME + " ASC";
            return new CursorLoader(
                    getBaseContext(), placeUri, new String[]{" * "}, null, null, order);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.d(TAG,"run here times ");
            placeAdapter.swapData(null, data, PlaceAdapter.MODE_LOG);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            placeAdapter.swapData(null, null, PlaceAdapter.MODE_LOG);
        }
    };

}
