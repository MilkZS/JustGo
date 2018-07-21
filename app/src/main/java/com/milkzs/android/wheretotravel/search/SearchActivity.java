package com.milkzs.android.wheretotravel.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.search.task.SearchNameTask;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.SearchViewListener {

    private String TAG = "SearchActivity";
    private ArrayAdapter<String> autoCompleteDataAdapter;
    private ArrayList<String> autoCompleteData = new ArrayList<>();
    private SearchNameTask searchNameTask;
    private SearchView searchView;
    private String userName = "root";
    private int default_num_history = 10;
    private int default_num_hot = 10;

    public static String FILE_SHARE = "search_tag_file";
    public static String FLAG_SEARCH_HISTORY = "flag_search_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.search_define_view);
        searchView.setClickListener(this);
        autoCompleteDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1
                , autoCompleteData);
        searchView.setAutoCompleteAdapter(autoCompleteDataAdapter);
        initShowSearchTagTip();
    }

    private void initShowSearchTagTip(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                FILE_SHARE, MODE_PRIVATE);
        String str = sharedPreferences.getString(FLAG_SEARCH_HISTORY, "");
        ArrayList<TextView> history = new ArrayList<>();
        for (String s : str.split(BaseInfo.SearchTAG.SPILT_HISTORY_TAG)) {
            if (!s.equals("")){
                TextView textView = new TextView(this);
                textView.setText(s);
                history.add(textView);
            }
        }

        String sHot = getString(R.string.hot_tag_ten);
        ArrayList<TextView> hot = new ArrayList<>();
        for (String s:sHot.split(BaseInfo.SearchTAG.SPILT_HOT_TAG)) {
            TextView textView = new TextView(this);
            textView.setText(s);
            hot.add(textView);
        }

        searchView.setHistoryList(history);
        searchView.setHotList(hot);
        searchView.initFlexboxLayout();
    }


    private void autoCompleteEditView(final String text) {

        if (searchNameTask != null) {
            searchNameTask.cancel(true);
            searchNameTask = null;
        }
        searchNameTask = new SearchNameTask(this, autoCompleteDataAdapter);
        searchNameTask.execute(text);
    }

    @Override
    public void startToSearch(String text) {
        //Toast.makeText(this,"search",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtra(BaseInfo.IntentFlag.FLAG_MODE_SEARCH_NAME, text);
        startActivity(intent);
    }

    @Override
    public void refreshSearchEdit(String text) {
        Log.d(TAG, "refresh data");
        autoCompleteEditView(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (searchNameTask != null) {
            searchNameTask.cancel(true);
            searchNameTask = null;
        }
    }
}
