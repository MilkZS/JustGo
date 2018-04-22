package com.milkzs.android.wheretotravel.Tool;

import android.content.Context;
import android.net.Uri;
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
    private static boolean DBG = false;
    private static ArrayList<Uri> arrayListPicUri;
    private static ArrayList<Uri> arrayListPicSmallUri;

    public static ArrayList<PlaceListInfo> getDataFromJson(String jsonString, Context context){
        try {
            if (DBG)Log.i(TAG,"then we would analyze json string is " + jsonString);
            JSONObject placeObject = new JSONObject(jsonString);
            JSONObject body = placeObject.getJSONObject(BaseInfo.QUERY_BODY);
            JSONObject page = body.getJSONObject(BaseInfo.QUERY_BODY_PAGE);
            JSONArray contentList = page.getJSONArray(BaseInfo.QUERY_BODY_PAGE_CONTENT_List);

            JSONObject singleOb;
            PlaceListInfo placeListInfo ;
            ArrayList<PlaceListInfo> placeListInfos = new ArrayList<>();
            for (int i=0;i<contentList.length();i++){
                singleOb = contentList.getJSONObject(i);
                placeListInfo = new PlaceListInfo();

                // name
                placeListInfo.setPlaceName(getJSONValue(singleOb,BaseInfo.CONTENT_LIST_PLACE_NAME));
                //summary
                placeListInfo.setSummary(getJSONValue(singleOb,BaseInfo.CONTENT_LIST_SUMMARY));
                //price
                String price = getJSONValue(singleOb,BaseInfo.CONTENT_LIST_PRICE);
                placeListInfo.setPrice(FormatData.formatPrice(price,context));
                //open time
                placeListInfo.setOpenTime(getJSONValue(singleOb,BaseInfo.CONTENT_LIST_OPEN_TIME));
                //address
                placeListInfo.setAddress(getJSONValue(singleOb,BaseInfo.CONTENT_LIST_ADDRESS));

                /** pictures uri */
                bindArrayPicUri(singleOb);
                placeListInfo.setPicListUrl(arrayListPicUri);
                placeListInfo.setPicListSmallUrl(arrayListPicSmallUri);

                placeListInfo.setMainPicUri(arrayListPicSmallUri.get(0));
                Log.d(TAG,"pic uri is " + arrayListPicSmallUri.get(0));

                placeListInfos.add(i,placeListInfo);
            }

            return placeListInfos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
        return BaseInfo.ERROR_SHOW;
    }

    /**
     * Add pic uri to array
     *
     * @param jsonObject
     * @throws JSONException
     */
    private static void bindArrayPicUri(JSONObject jsonObject) throws JSONException {
        JSONArray picArray = jsonObject.getJSONArray(BaseInfo.CONTENT_LIST_PIC_LIST);
        arrayListPicUri = new ArrayList<>();
        arrayListPicSmallUri = new ArrayList<>();
        for(int i=0;i<picArray.length();i++){
            JSONObject uriOB = picArray.getJSONObject(i);
            arrayListPicUri.add(
                    i,Uri.parse(uriOB.getString(BaseInfo.CONTENT_LIST_PIC_LIST__URI)));
            arrayListPicSmallUri.add(
                    i,Uri.parse(uriOB.getString(BaseInfo.CONTENT_LIST_PIC_LIST_SMALL_URI)));
        }
    }
}
