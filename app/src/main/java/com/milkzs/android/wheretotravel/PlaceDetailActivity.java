package com.milkzs.android.wheretotravel;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.DetailFragment.ContentDetailFragment;
import com.milkzs.android.wheretotravel.DetailFragment.LogListFragment;
import com.milkzs.android.wheretotravel.DetailFragment.MessageDetailFragment;
import com.milkzs.android.wheretotravel.DetailFragment.PicturesDetailFragment;
import com.milkzs.android.wheretotravel.Tool.FormatData;
import com.milkzs.android.wheretotravel.adapter.DetailViewPageAdapter;
import com.milkzs.android.wheretotravel.db.PlaceContract;

import java.util.ArrayList;

public class PlaceDetailActivity extends AppCompatActivity {

    private String TAG = "PlaceDetailActivity";
    private boolean DBG = false;

    private ViewPager viewPager;
    private ArrayList<Fragment> viewArrayList;
    private TabLayout tabLayout;
    private ArrayList<String> tabList;
    private Cursor cursor;

    private int tabShowSum = 4;// sum of tab title
    private int tabShowCount = 4; // counts of show tab at one time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        viewPager = findViewById(R.id.detail_view_page);
        tabLayout = findViewById(R.id.detail_tab_layout);

        Intent intent = getIntent();
        PlaceListInfo placeListInfo =
                intent.getParcelableExtra(BaseInfo.IntentFlag.FLAG_ARRAY_LIST_DETAIL);

        tabList = FormatData.formatTabString(this);
        Log.d(TAG,"tab list length is " + tabList.size());
        initViewPage(placeListInfo);
        initTabLayout();
    }

    /**
     * init view page
     *
     * @param placeListInfo
     */
    private void initViewPage(PlaceListInfo placeListInfo){
        viewArrayList = new ArrayList<>();
        viewArrayList.add(MessageDetailFragment.newInstance(placeListInfo));
        viewArrayList.add(ContentDetailFragment.newInstance(placeListInfo));
        viewArrayList.add(PicturesDetailFragment.newInstance(placeListInfo));
        viewArrayList.add(LogListFragment.newInstance(placeListInfo));
        DetailViewPageAdapter detailViewPageAdapter = new DetailViewPageAdapter(
                getSupportFragmentManager(),viewArrayList);
        viewPager.setAdapter(detailViewPageAdapter);
    }

    private void initTabLayout(){
        // whether tab can scrollable
        tabLayout.setTabMode(
                tabShowCount <= tabShowSum ? TabLayout.MODE_FIXED:TabLayout.MODE_SCROLLABLE) ;
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_tab_detail));
        tabLayout.setSelectedTabIndicatorHeight((int) getResources().getDimension(R.dimen.detail_tab_layout_select));

        // connect view page with tab layout
        tabLayout.setupWithViewPager(viewPager);
        for (int i=0;i<tabList.size();i++){
            Log.d(TAG,"tablist index i is " + tabList.get(i));
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            TextView textView = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.detail_tab_layout,tabLayout,false);
            textView.setText(tabList.get(i));
            tab.setCustomView(textView);
        }
    }
}
