package com.milkzs.android.wheretotravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;

public class PlaceDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        Intent intent = getIntent();
        PlaceListInfo placeListInfo = (PlaceListInfo) intent.getSerializableExtra(
                BaseInfo.IntentFlag.FLAG_ARRAY_LIST_DETAIL);



    }

    private void bindDataToUI(){

    }
}
