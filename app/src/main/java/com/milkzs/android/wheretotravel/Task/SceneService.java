package com.milkzs.android.wheretotravel.Task;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.milkzs.android.wheretotravel.Tool.AnalysisJsonData;
import com.milkzs.android.wheretotravel.Tool.DataRequest;

import java.io.IOException;
import java.net.URL;

/**
 * Created by alan on 2018/7/24.
 */

public class SceneService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SceneService() {
        super("SceneService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    private synchronized void syncSceneData() {
        try {
            URL url = DataRequest.buildUriForShowApi();
            String sJson = DataRequest.getResponseFromHttpUrl(url);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
