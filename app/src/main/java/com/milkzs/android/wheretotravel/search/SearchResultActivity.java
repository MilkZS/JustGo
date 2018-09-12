package com.milkzs.android.wheretotravel.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.Task.QueryDataTask;
import com.milkzs.android.wheretotravel.adapter.PlaceAdapter;

public class SearchResultActivity extends AppCompatActivity implements PlaceAdapter.ClickTranform {

    private QueryDataTask queryDataTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        String text = intent.getStringExtra(BaseInfo.IntentFlag.FLAG_MODE_SEARCH_NAME);

        RecyclerView recyclerView = findViewById(R.id.result_search_recycler_view);
        recyclerView.setHasFixedSize(true);

        //PlaceAdapter placeAdapter = new PlaceAdapter(this);
        SearchPlaceAdapter searchPlaceAdapter = new SearchPlaceAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchPlaceAdapter);

        if(queryDataTask != null){
            queryDataTask = null;
        }

        ProgressBar progressBar = findViewById(R.id.progress_bar_detail);

        queryDataTask = new QueryDataTask(this,recyclerView,progressBar);
        queryDataTask.execute(text);
    }

    @Override
    public void onClick(int position,String sceneName) {

    }
}
