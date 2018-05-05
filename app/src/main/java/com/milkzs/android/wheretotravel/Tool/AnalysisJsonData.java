package com.milkzs.android.wheretotravel.Tool;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.db.PlaceContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

                //id
                String sid = getJSONValue(singleOb,BaseInfo.QUERY_ID);
                placeListInfo.setsId(sid);
                Uri uri = PlaceContract.PlaceBase.CONTENT_BASE.buildUpon().appendPath(sid).build();
                Log.i(TAG,"query string is " + uri.toString());
                Cursor cursor = context.getContentResolver().query(
                        uri,
                        null,
                        null,null,null);
                Map<String,String> map = readDataFromCursor(cursor);
                placeListInfo.setLogMap(map);

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

                //discount
                placeListInfo.setDiscount(getJSONValue(singleOb,BaseInfo.CONTENT_LIST_DISCOUNT));
                //attention
                placeListInfo.setAttention(getJSONValue(singleOb,BaseInfo.CONTENT_LIST_ATTENTION));
                //content
                placeListInfo.setDetailContent(getJSONValue(singleOb,BaseInfo.CONTENT_LIST_CONTENT));

                // for location
                JSONObject locationOB = getObject(singleOb,BaseInfo.LOCATION);
                if(locationOB != null){
                    placeListInfo.setLocation_lon(getJSONValue(locationOB,BaseInfo.LOCATION_LON));
                    placeListInfo.setLocation_lat(getJSONValue(locationOB,BaseInfo.LOCATION_LAT));
                }else {
                    placeListInfo.setLocation_lon("0");
                    placeListInfo.setLocation_lat("0");
                }

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

    /**
     * Get object from json object
     *
     * @param jsonObject
     * @param param
     * @return
     * @throws JSONException
     */
    private static JSONObject getObject(JSONObject jsonObject,String param) throws JSONException {
        if(jsonObject.has(param)){
            return jsonObject.getJSONObject(param);
        }
        return null;
    }

    private static Map<String,String> readDataFromCursor(Cursor cursor){
        Map<String,String> map = new HashMap<>();
        if(null == cursor || cursor.getCount() == 0){
            map.put(BaseInfo.MapFlag.FLAG_EXISTS,"0");
            map.put(BaseInfo.MapFlag.FLAG_TIME_ARR,"");
            map.put(BaseInfo.MapFlag.FLAG_TIME_GO,"");
            return map;
        }
        cursor.moveToFirst();
        Log.d(TAG,"cursor length is " + cursor.getCount());
        forDebug(cursor);
        Log.d(TAG,"cursor COLUMN_PLACE_TIME index is  "+cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_TIME));

        map.put(BaseInfo.MapFlag.FLAG_EXISTS,"1");
        map.put(BaseInfo.MapFlag.FLAG_TIME_ARR,
                cursor.getString(cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_TIME)));
        map.put(BaseInfo.MapFlag.FLAG_TIME_GO,
                cursor.getString(cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_TIME_GO)));
        return map;
    }

    private static void forDebug(Cursor cursor){
        for(int i=0;i<cursor.getCount();i++){
            Log.d(TAG,"cursor index = " + i +" and content is " + cursor.getString(i));
        }
    }

}
