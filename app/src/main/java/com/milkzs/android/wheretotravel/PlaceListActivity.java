package com.milkzs.android.wheretotravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.Task.QueryDataTask;
import com.milkzs.android.wheretotravel.Titanic.Titanic;
import com.milkzs.android.wheretotravel.Titanic.TitanicTextView;
import com.milkzs.android.wheretotravel.adapter.PlaceAdapter;


public class PlaceListActivity extends AppCompatActivity implements PlaceAdapter.ClickTranform{

    private PlaceAdapter placeAdapter;
    private RecyclerView recyclerView;
    private boolean DBG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        recyclerView = findViewById(R.id.main_recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this,getResources().getInteger(R.integer.grid_layout_span_list));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        placeAdapter = new PlaceAdapter(this);
        recyclerView.setAdapter(placeAdapter);

        TitanicTextView titanicTextView = findViewById(R.id.before_main_show);
        new QueryDataTask(this,placeAdapter,titanicTextView).execute();

    }

    @Override
    public void onClick(int position, PlaceListInfo placeListInfo) {
        if (DBG)Toast.makeText(this,""+position,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,PlaceDetailActivity.class);
        intent.putExtra(BaseInfo.IntentFlag.FLAG_ARRAY_LIST_DETAIL,placeListInfo);
        startActivity(intent);
    }
}
