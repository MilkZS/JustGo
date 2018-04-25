package com.milkzs.android.wheretotravel.Task;

import android.content.Context;
import android.os.AsyncTask;

import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.Tool.AnalysisJsonData;
import com.milkzs.android.wheretotravel.Tool.DataRequest;
import com.milkzs.android.wheretotravel.adapter.PlaceAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by milkdz on 2018/4/21.
 */

public class QueryDataTask extends AsyncTask<Void, Void, ArrayList<PlaceListInfo>> {

    private Context context;
    private PlaceAdapter placeAdapter;

    public QueryDataTask(Context context,PlaceAdapter placeAdapter) {
        this.context = context;
        this.placeAdapter = placeAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<PlaceListInfo> doInBackground(Void... voids) {

        try {
            String jsonString = DataRequest.getResponseFromHttpUrl(
                    DataRequest.buildUriForShowApi());
            ArrayList<PlaceListInfo> arrayList =
                    AnalysisJsonData.getDataFromJson(jsonString, context);
            return arrayList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<PlaceListInfo> arrayList) {
        super.onPostExecute(arrayList);
        if(arrayList != null){
            placeAdapter.swapData(arrayList);
        }
    }
}
