package com.milkzs.android.wheretotravel;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.support.design.widget.TabLayout;


public class PlaceMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_main_page);

        init();
    }

    private void init(){

        ViewPager viewPager = findViewById(R.id.view_page_main);

        TabLayout tableLayout = findViewById(R.id.tab_layout_main);


    }
}
