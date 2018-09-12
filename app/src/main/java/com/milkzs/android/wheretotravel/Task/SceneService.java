package com.milkzs.android.wheretotravel.Task;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.milkzs.android.wheretotravel.Tool.AnalysisJsonData;
import com.milkzs.android.wheretotravel.Tool.DataRequest;
import com.milkzs.android.wheretotravel.db.PlaceContract;
import com.milkzs.android.wheretotravel.imageLoad.ImageLoader;

import java.io.IOException;
import java.net.URL;

/**
 * Created by alan on 2018/7/24.
 */

public class SceneService extends IntentService {

    public static String FLAG_SCENE_KEYWORD = "scene_search_key_word";

    private String keyWord = "";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SceneService() {
        super("SceneService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null){
            keyWord = intent.getStringExtra(FLAG_SCENE_KEYWORD);
        }
        syncSceneData();
    }



    /**
     * delete old scene data and insert new scene data.
     */
    private synchronized void syncSceneData() {
        try {
            URL url;
            if(keyWord.equals("")){
                url = DataRequest.buildUriForShowApi();
            }else{
                url = DataRequest.buildURIForSearchKeyword(keyWord);
            }

            String sJson = DataRequest.getResponseFromHttpUrl(url);
            ContentValues[] contentValues =
                    AnalysisJsonData.getContentValuesFromJson(sJson, getApplicationContext());
            if (contentValues != null) {
                getApplicationContext().getContentResolver().delete(
                        PlaceContract.SceneBase.CONTENT_BASE, null, null);
                getApplicationContext().getContentResolver().bulkInsert(
                        PlaceContract.SceneBase.CONTENT_BASE, contentValues);
            }
            ContentValues[] uriContentValue = AnalysisJsonData.getContentValuesOfPictures(sJson,getApplicationContext());
            if (uriContentValue != null) {
                getApplicationContext().getContentResolver().delete(
                        PlaceContract.SceneImgBase.CONTENT_BASE, null, null);
                getApplicationContext().getContentResolver().bulkInsert(
                        PlaceContract.SceneImgBase.CONTENT_BASE, uriContentValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
