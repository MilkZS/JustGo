package com.milkzs.android.wheretotravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.milkzs.android.wheretotravel.adapter.PlaceAdapter;

public class PlaceListActivity extends AppCompatActivity {

    private PlaceAdapter placeAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        recyclerView = findViewById(R.id.main_recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this,getResources().getInteger(R.integer.grid_layout_span));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        placeAdapter = new PlaceAdapter();
        recyclerView.setAdapter(placeAdapter);

        new QueryDataTask(this,placeAdapter).execute();

    }
}
