package com.milkzs.android.wheretotravel.search;

import android.content.Intent;
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

    private int default_num_history = 10;
    private int default_num_hot = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView searchView = findViewById(R.id.search_define_view);
        searchView.setClickListener(this);
        autoCompleteDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1
                ,autoCompleteData );
        searchView.setAutoCompleteAdapter(autoCompleteDataAdapter);

        ArrayList<TextView> histoy = new ArrayList<>();
        for(int i=0;i<default_num_history;i++){
            TextView textView = new TextView(this);
            textView.setText("文艺青年");
            histoy.add(textView);
        }

        ArrayList<TextView> hot = new ArrayList<>();
        for(int i=0;i<default_num_history;i++){
            TextView textView = new TextView(this);
            textView.setText("文艺青年");
            hot.add(textView);
        }

        searchView.setHistoryList(histoy);
        searchView.setHotList(hot);
        searchView.initFlexboxLayout();
    }



    private void autoCompleteEditView(final String text) {

        if(searchNameTask != null){
            searchNameTask.cancel(true);
            searchNameTask = null;
        }
        searchNameTask = new SearchNameTask(this,autoCompleteDataAdapter);
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
        if(searchNameTask != null){
            searchNameTask.cancel(true);
            searchNameTask = null;
        }
    }
}
