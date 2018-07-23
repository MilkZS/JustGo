package com.milkzs.android.wheretotravel.db;

import android.net.Uri;
import android.provider.BaseColumns;

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
        public static String COLUMN_PLACE_TIME = "place_time_arr";
        public static String COLUMN_PLACE_TIME_GO = "place_time_go";
        public static String COLUMN_PLACE_NAME = "place_name";

        public static String[] QUERY_ENTRY = {
                _ID,
                COLUMN_PLACE_ID,
                COLUMN_PLACE_NAME,
                COLUMN_PLACE_TIME,
                COLUMN_PLACE_TIME_GO
        };
    }

    public static final class SceneBase implements BaseColumns{
        public static String SCENE_INFO = "scene";
        public static String TABLE_NAME = "where_to_travel_scene";

        public static final Uri CONTENT_BASE = CONTENT_BASE_URI
                .buildUpon().appendPath(SCENE_INFO).build();

        public static String COLUMN_SCENE_ID = "scene_id";
        public static String COLUMN_SCENE_NAME = "scene_name";
        public static String COLUMN_SCENE_SUMMERY = "scene_summery";
        public static String COLUMN_SCENE_PRICE = "scene_price";
        public static String COLUMN_SCENE_OPEN_TIME = "scene_open_time";
        public static String COLUMN_SCENE_ADDRESS = "scene_address";
        public static String COLUMN_SCENE_PICTURE_SRC = "scene_picture_src";
        public static String COLUMN_SCENE_DISCOUNT = "scene_discount";
        public static String COLUMN_SCENE_ATTENTION = "scene_attention";
        public static String COLUMN_SCENE_CONTENT = "scene_content";

        public static String[] QUERY_ENTRY = {
                _ID,
                COLUMN_SCENE_ID,
                COLUMN_SCENE_NAME,
                COLUMN_SCENE_SUMMERY,
                COLUMN_SCENE_PRICE,
                COLUMN_SCENE_OPEN_TIME,
                COLUMN_SCENE_ADDRESS,
                COLUMN_SCENE_PICTURE_SRC,
                COLUMN_SCENE_DISCOUNT,
                COLUMN_SCENE_ATTENTION,
                COLUMN_SCENE_CONTENT
        };
    }
}
