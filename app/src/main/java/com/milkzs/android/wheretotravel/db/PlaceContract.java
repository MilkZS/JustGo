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

    public static final class SearchTAG implements BaseColumns{
        public static String SEARCH_INFO = "searchTag";
        public static String TABLE_NAME = "where_to_travel_search_tag";

        public static final Uri CONTENT_BASE = CONTENT_BASE_URI.buildUpon()
                .appendPath(SEARCH_INFO).build();

        public static String COLUMN_ID = "search_tag_id";
        public static String COLUMN_TAG = "search_tag_tags";
        public static String COLUMN_USER_ID = "search_tag_userId";

        public static String[] QUERY_ENTRY = {
                _ID,
                COLUMN_ID,
                COLUMN_TAG,
                COLUMN_USER_ID
        };
    }
}
