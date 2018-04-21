package com.milkzs.android.wheretotravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlaceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        new QueryDataTask().execute();
    }
}
