package com.milkzs.android.wheretotravel.db;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URL;

/**
 * Created by milkdz on 2018/5/1.
 */

public class PlaceContract {

    public static final String CONTENT_AUTHORITY = "com.milkzs.android.wheretotravel";
    public static final Uri CONTENT_BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class PlaceBase implements BaseColumns{
        public static String PLACE_INFO = "place";
        public static String TABLE_NAME = "where_to_travel_log";

        public static final Uri CONTENT_BASE = CONTENT_BASE_URI.buildUpon()
                .appendPath(PLACE_INFO).build();

        public static String COLUMN_PLACE_ID = "place_Sid";
        public static String COLUMN_PLACE_PIC = "place_main_pic";
        public static String COLUMN_PLACE_TIME = "place_time_arr";
        public static String COLUMN_PLACE_TIME_GO = "place_time_go";
        public static String COLUMN_PLACE_NAME = "place_name";

        public static String[] QUERY_ENTRY = {
                _ID,
                COLUMN_PLACE_ID,
                COLUMN_PLACE_PIC,
                COLUMN_PLACE_NAME,
                COLUMN_PLACE_TIME,
                COLUMN_PLACE_TIME_GO
        };
    }

    public static final class SceneBase implements BaseColumns{
        public static String SCENE_INFO = "scene";
        public static String TABLE_NAME = "scene_full_info";

        public static final Uri CONTENT_BASE = CONTENT_BASE_URI
                .buildUpon().appendPath(SCENE_INFO).build();

        public static String COLUMN_SCENE_ID = "scene_id";
        public static String COLUMN_SCENE_NAME = "scene_name";
        public static String COLUMN_SCENE_SUMMERY = "scene_summery";
        public static String COLUMN_SCENE_PRICE = "scene_price";
        public static String COLUMN_SCENE_OPEN_TIME = "scene_open_time";
        public static String COLUMN_SCENE_ADDRESS = "scene_address";
        public static String COLUMN_SCENE_DISCOUNT = "scene_discount";
        public static String COLUMN_SCENE_ATTENTION = "scene_attention";
        public static String COLUMN_SCENE_CONTENT = "scene_content";
        public static String COLUMN_SCENE_MAIN_PIC = "scene_main_pic";
        public static String COLUMN_SCENE_LOCATION_LON = "scene_location_lon";
        public static String COLUMN_SCENE_LOCATION_LAT = "scene_location_lat";
        public static String COLUMN_SCENE_LOG_TIME = "scene_log_time";


        public static String[] QUERY_ENTRY = {
                _ID,
                COLUMN_SCENE_ID,
                COLUMN_SCENE_NAME,
                COLUMN_SCENE_SUMMERY,
                COLUMN_SCENE_PRICE,
                COLUMN_SCENE_OPEN_TIME,
                COLUMN_SCENE_ADDRESS,
                COLUMN_SCENE_DISCOUNT,
                COLUMN_SCENE_ATTENTION,
                COLUMN_SCENE_CONTENT,
                COLUMN_SCENE_MAIN_PIC,
                COLUMN_SCENE_LOCATION_LON,
                COLUMN_SCENE_LOCATION_LAT,
                COLUMN_SCENE_LOG_TIME
        };
    }

    public static final class SceneImgBase implements BaseColumns{

        public static String SCENEIMG_INFO = "scene_img_info";
        public static String TABLE_NAME = "scene_img_url";

        public static final Uri CONTENT_BASE = CONTENT_BASE_URI.buildUpon()
                .appendPath(SCENEIMG_INFO).build();

        public static String COLUMN_SCENE_ID = "scene_img_id";
        public static String COLUMN_SCENE_IMG_URI = "scene_img_uri";
        public static String COLUMN_SCENE_IMG_BIG_URL = "scene_img_big_uri";

        public static String[] QUERY_ENTRY = {
                _ID,
                COLUMN_SCENE_ID,
                COLUMN_SCENE_IMG_URI,
                COLUMN_SCENE_IMG_BIG_URL
        };
    }
}
