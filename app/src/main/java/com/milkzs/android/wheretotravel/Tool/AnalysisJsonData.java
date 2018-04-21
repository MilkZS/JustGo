package com.milkzs.android.wheretotravel.Tool;

import android.util.Log;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/4/21.
 */

public class AnalysisJsonData {

    private static String TAG = "AnalysisJsonData";
    private static boolean DBG = true;



    public static void getDataFromJson(String jsonString){
        try {
            Log.i(TAG,"then we would analyze json string is " + jsonString);
            JSONObject placeObject = new JSONObject(jsonString);
            JSONObject body = placeObject.getJSONObject(BaseInfo.QUERY_BODY);
            JSONObject page = body.getJSONObject(BaseInfo.QUERY_BODY_PAGE);
            JSONArray contentList = page.getJSONArray(BaseInfo.QUERY_BODY_PAGE_CONTENT_List);

            JSONObject singleOb;
            PlaceListInfo placeListInfo = new PlaceListInfo();
            ArrayList<PlaceListInfo> placeListInfos = new ArrayList<>();
            for (int i=0;i<contentList.length();i++){
                singleOb = contentList.getJSONObject(i);
                String name = getJSONValue(singleOb,BaseInfo.CONTENT_LIST_PLACE_NAME);
                String summary = getJSONValue(singleOb,BaseInfo.CONTENT_LIST_SUMMARY);
                String price = getJSONValue(singleOb,BaseInfo.CONTENT_LIST_PRICE);
                String opentime = getJSONValue(singleOb,BaseInfo.CONTENT_LIST_OPEN_TIME);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get value from JSON object by param
     *
     * @param jsonObject
     * @param param
     * @return
     * @throws JSONException
     */
    private static String getJSONValue(JSONObject jsonObject,String param) throws JSONException {
        if(jsonObject.has(param)){
            return jsonObject.getString(param);
        }
        return "NONE";
    }
}
