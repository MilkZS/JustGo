package com.milkzs.android.wheretotravel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.transition.Scene;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.Task.QueryDataTask;
import com.milkzs.android.wheretotravel.Titanic.TitanicTextView;
import com.milkzs.android.wheretotravel.adapter.PlaceAdapter;
import com.milkzs.android.wheretotravel.db.PlaceContract;

import com.google.android.gms.ads.MobileAds;
import com.milkzs.android.wheretotravel.search.SearchActivity;

public class PlaceListActivity extends AppCompatActivity
        implements PlaceAdapter.ClickTranform, LoaderManager.LoaderCallbacks<Cursor> {

    private String TAG = "PlaceListActivity";
    private boolean DBG = false;

    private PlaceAdapter placeAdapter;
    private RecyclerView recyclerView;

    private QueryDataTask queryDataTask;
    private TitanicTextView titanicTextView;
    private ImageView searchImageView;

    private GridLayoutManager gridLayoutManager;

    private int position = RecyclerView.NO_POSITION;
    private String POSITION_FLAG = "flag_position";
    private String SHARED_FILE = "share_file";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        Log.e(TAG,"this is onCreate");

        searchImageView = findViewById(R.id.bar_search_img);
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceListActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        MobileAds.initialize(this, getResources().getString(R.string.sign_build_id));
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getResources().getString(R.string.sign_app_id));
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sharedPreferences = getSharedPreferences(SHARED_FILE, MODE_PRIVATE);
        position = sharedPreferences.getInt(POSITION_FLAG, 0);
        Log.d(TAG,"get position is " + position);

        recyclerView = findViewById(R.id.main_recycler);

        gridLayoutManager = new GridLayoutManager(
                this, getResources().getInteger(R.integer.grid_layout_span_list));
        recyclerView.setHasFixedSize(true);

        titanicTextView = findViewById(R.id.before_main_show);
        refreshMode(PlaceAdapter.MODE_LIST);
    }

    /**
     * chose mode to run
     *
     * @param mode
     */
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
                queryDataTask = new QueryDataTask(this, placeAdapter,
                        titanicTextView,recyclerView,position,QueryDataTask.MODE_SEARCH_DEFAULT);
                queryDataTask.execute("");
//                recyclerView.smoothScrollToPosition(position);
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
                getSupportLoaderManager().initLoader(0, null, this);
            }
            break;
        }
    }

    @Override
    public void onClick(int position, PlaceListInfo placeListInfo) {
        // Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PlaceDetailActivity.class);
        intent.putExtra(BaseInfo.IntentFlag.FLAG_ARRAY_LIST_DETAIL, placeListInfo);
        startActivity(intent);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        Scene scene = Scene.getSceneForLayout(viewGroup,R.layout.detail_view_page_message,this);
        TransitionSet transitionSet = (TransitionSet) TransitionInflater.from(viewGroup.getContext()).inflateTransition(R.transition.detail_transition);
        TransitionManager.go(scene,transitionSet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_list:{
                refreshMode(PlaceAdapter.MODE_LIST);
            }break;
            case R.id.item_log:{
                refreshMode(PlaceAdapter.MODE_LOG);
            }break;
            case R.id.item_search:{
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
            }break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(TAG,"this is onSaveInstanceState");
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_FILE, MODE_PRIVATE);
        position = ((GridLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        if (DBG) Toast.makeText(this,"position is " + position,Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(POSITION_FLAG, position);
        editor.apply();
        editor.commit();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"this is onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"this is onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"this is onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"this is onStart ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"this is onRestart ");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri placeUri = PlaceContract.PlaceBase.CONTENT_BASE;
        String order = PlaceContract.PlaceBase.COLUMN_PLACE_TIME + " ASC";
        return new CursorLoader(
                getBaseContext(), placeUri, new String[]{" * "}, null, null, order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "run here times ");
        placeAdapter.swapData(null, data, PlaceAdapter.MODE_LOG);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        placeAdapter.swapData(null, null, PlaceAdapter.MODE_LOG);
    }

}
