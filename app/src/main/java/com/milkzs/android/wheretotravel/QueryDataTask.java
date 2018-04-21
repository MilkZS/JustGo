package com.milkzs.android.wheretotravel;

import android.os.AsyncTask;

import com.milkzs.android.wheretotravel.Tool.AnalysisJsonData;
import com.milkzs.android.wheretotravel.Tool.DataRequest;

import java.io.IOException;

/**
 * Created by milkdz on 2018/4/21.
 */

public class QueryDataTask extends AsyncTask<Void,Void,Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String s = DataRequest.getResponseFromHttpUrl(DataRequest.buildUriForShowApi());
            AnalysisJsonData.getDataFromJson(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
