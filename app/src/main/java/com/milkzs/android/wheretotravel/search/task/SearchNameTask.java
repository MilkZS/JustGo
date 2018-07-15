package com.milkzs.android.wheretotravel.search.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.milkzs.android.wheretotravel.Tool.AnalysisJsonData;
import com.milkzs.android.wheretotravel.Tool.DataRequest;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by milkz on 2018/7/14.
 */

public class SearchNameTask extends AsyncTask<String, Void, ArrayList<String>> {

    private Context context;
    private ArrayAdapter<String> autoDataAdapter;

    public SearchNameTask(Context context, ArrayAdapter<String> autoDataAdapter) {
        this.context = context;
        this.autoDataAdapter = autoDataAdapter;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        String sText = "";
        if (strings.length != 0) {
            sText = strings[0];
        }
        try {
            String jsonString = DataRequest.getResponseFromHttpUrl(
                    DataRequest.buildURIForSearchKeyword(sText));
            return AnalysisJsonData.getNameFromJson(jsonString, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        if (strings != null) {
            autoDataAdapter.clear();
            autoDataAdapter.addAll(strings);
            autoDataAdapter.notifyDataSetChanged();
        }
    }
}
