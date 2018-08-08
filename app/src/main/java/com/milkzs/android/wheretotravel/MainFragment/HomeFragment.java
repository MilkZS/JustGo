package com.milkzs.android.wheretotravel.MainFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.milkzs.android.wheretotravel.PlaceDetailActivity;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.Task.QueryDataTask;
import com.milkzs.android.wheretotravel.Titanic.TitanicTextView;
import com.milkzs.android.wheretotravel.adapter.PlaceAdapter;
import com.milkzs.android.wheretotravel.db.PlaceContract;
import com.milkzs.android.wheretotravel.search.SearchActivity;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment implements PlaceAdapter.ClickTranform, LoaderManager.LoaderCallbacks<Cursor> {

    private String TAG = "HomeFragment";

    private RecyclerView recyclerView;


    private GridLayoutManager gridLayoutManager;

    private int position = RecyclerView.NO_POSITION;
    private String POSITION_FLAG = "flag_position";
    private String SHARED_FILE = "share_file";

    private View view;

    private PlaceAdapter placeAdapter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
        }

        // Inflate the layout for this fragment
        ImageView searchImageView = view.findViewById(R.id.bar_search_img);
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        MobileAds.initialize(view.getContext(), getResources().getString(R.string.sign_build_id));
        AdView adView = new AdView(view.getContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getResources().getString(R.string.sign_app_id));
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Spinner locateSpinner = view.findViewById(R.id.bar_location_local_spinner);
        String[] sArr = getResources().getStringArray(R.array.spinner_locate_province);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                view.getContext(), R.layout.support_simple_spinner_dropdown_item, sArr);
        locateSpinner.setAdapter(arrayAdapter);
        locateSpinner.setSelection(0, true);

        SharedPreferences sharedPreferences =
                view.getContext().getSharedPreferences(SHARED_FILE, MODE_PRIVATE);
        position = sharedPreferences.getInt(POSITION_FLAG, 0);
        Log.d(TAG, "get position is " + position);

        recyclerView = view.findViewById(R.id.main_recycler);

        gridLayoutManager = new GridLayoutManager(
                view.getContext(), getResources().getInteger(R.integer.grid_layout_span_list));
        recyclerView.setHasFixedSize(true);

       // titanicTextView = view.findViewById(R.id.before_main_show);
        refreshMode(view);
        return view;
    }

    private void refreshMode(View view) {
        gridLayoutManager.setSpanCount(
                getResources().getInteger(R.integer.grid_layout_span_list));
        recyclerView.setLayoutManager(gridLayoutManager);
        placeAdapter = new PlaceAdapter(this);
        recyclerView.setAdapter(placeAdapter);
        getLoaderManager().initLoader(0, null, this);
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
    public void onClick(int position) {
        //Toast.makeText(getContext(),"click success",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(view.getContext(), PlaceDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "this is onSaveInstanceState");
        SharedPreferences sharedPreferences = view.getContext()
                .getSharedPreferences(SHARED_FILE, MODE_PRIVATE);
        position = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(POSITION_FLAG, position);
        editor.apply();
        editor.commit();
        super.onSaveInstanceState(outState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = PlaceContract.SceneBase.CONTENT_BASE;
        String order = PlaceContract.SceneBase.COLUMN_SCENE_ID + " ASC ";
        return new CursorLoader(getContext(), uri, new String[]{"*"}, null, null, order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        placeAdapter.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
