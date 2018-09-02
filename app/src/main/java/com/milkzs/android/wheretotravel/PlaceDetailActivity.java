package com.milkzs.android.wheretotravel;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.milkzs.android.wheretotravel.DetailFragment.ContentDetailFragment;
import com.milkzs.android.wheretotravel.DetailFragment.MessageDetailFragment;
import com.milkzs.android.wheretotravel.Tool.FormatData;
import com.milkzs.android.wheretotravel.adapter.DetailViewPageAdapter;

import java.util.ArrayList;

public class PlaceDetailActivity extends AppCompatActivity {

    private String TAG = "PlaceDetailActivity";
    private boolean DBG = false;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<String> tabList;

    public static String FLAG_SCENE_DETAIL_SCENE_ID = "flag_scene_detail_scene_id";
    public static String FLAG_SCENE_DETAIL_SCENE_NAMWE = "flag_scene_detail_name";

    private int sceneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        Toolbar mToolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.detail_view_page);
        tabLayout = findViewById(R.id.detail_tab_layout);

        Intent intent = getIntent();
        String sceneName = intent.getStringExtra(FLAG_SCENE_DETAIL_SCENE_NAMWE);

        TextView mTitleName = findViewById(R.id.detail_title_name);
        mTitleName.setText(sceneName);

        tabList = FormatData.formatTabString(this);
        Log.d(TAG,"tab list length is " + tabList.size());

        sceneId = intent.getIntExtra(FLAG_SCENE_DETAIL_SCENE_ID,0);
        Log.d(TAG,"scene id = " + sceneId);
        initViewPage(sceneId);
        initTabLayout();
    }

    /**
     * init view page
     */
    private void initViewPage(int sceneId){
        ArrayList<Fragment> viewArrayList = new ArrayList<>();
        viewArrayList.add(MessageDetailFragment.newInstance(sceneId));
        viewArrayList.add(ContentDetailFragment.newInstance(sceneId));
        //viewArrayList.add(PicturesDetailFragment.newInstance(placeListInfo));
        //viewArrayList.add(LogListFragment.newInstance(placeListInfo));
        DetailViewPageAdapter detailViewPageAdapter = new DetailViewPageAdapter(
                getSupportFragmentManager(), viewArrayList);
        viewPager.setAdapter(detailViewPageAdapter);
    }

    private void initTabLayout(){
        // whether tab can scrollable
        int tabShowSum = 4;
        int tabShowCount = 4; // counts of show tab at one time
        tabLayout.setTabMode(
                tabShowCount <= tabShowSum ? TabLayout.MODE_FIXED:TabLayout.MODE_SCROLLABLE) ;
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_tab_detail));
        tabLayout.setSelectedTabIndicatorHeight((int) getResources().getDimension(R.dimen.detail_tab_layout_select));

        // connect view page with tab layout
        tabLayout.setupWithViewPager(viewPager);
        for (int i=0;i<2;i++){
            Log.d(TAG,"tablist index i is " + tabList.get(i));
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            TextView textView = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.detail_tab_layout,tabLayout,false);
            textView.setText(tabList.get(i));
            tab.setCustomView(textView);
        }
    }
}
