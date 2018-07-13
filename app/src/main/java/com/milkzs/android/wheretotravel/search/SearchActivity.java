package com.milkzs.android.wheretotravel.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.SearchViewListener {

    private ArrayAdapter<String> autoCompleteDataAdapter;
    private ArrayList<String> autoCompleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView searchView = findViewById(R.id.search_define_view);
        searchView.setClickListener(this);
    }

    private void autoCompleteEditView(String text){
        if(autoCompleteData == null){
            autoCompleteData = new ArrayList<>();
        }else{

        }

        if(autoCompleteDataAdapter == null){
            autoCompleteDataAdapter =
                    new ArrayAdapter<>(this,R.layout.search_view,autoCompleteData);
        }else{
            autoCompleteDataAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void startToSearch(String text) {
        //Toast.makeText(this,"search",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
        intent.putExtra(BaseInfo.IntentFlag.FLAG_MODE_SEARCH_NAME,text);
        startActivity(intent);
    }

    @Override
    public void refreshSearchEdit(String text) {
        autoCompleteEditView(text);
    }
}
