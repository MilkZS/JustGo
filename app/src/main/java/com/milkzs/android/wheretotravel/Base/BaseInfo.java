package com.milkzs.android.wheretotravel.Base;

/**
 * Created by milkdz on 2018/4/21.
 */

public interface BaseInfo {

    /** error picture */
    String ERROR_PIC = "http://pic3.40017.cn/scenery/destination/2015/04/16/09/kE682I.jpg";

    /** base param for query */
    String BASE_URI = "http://route.showapi.com/268-1";
    String SHOWAPI_API_ID = "showapi_appid";
    String API_ID = "62568"; // <----  api id
    String SHOWAPI_API_SIGN = "showapi_sign";
    String SHOWAPI_NAME = "keyword";
    String API_SIGN = "6cf04f645e6341f0ae8d882d13db6aff";// <---- api sign

    /** @link {http://route.showapi.com/268-1?showapi_appid=62568
     * &showapi_sign=6cf04f645e6341f0ae8d882d13db6aff}*/
    String SHOWAPI_QUERY_MAIN = BASE_URI + "?"
            + SHOWAPI_API_ID + "=" + API_ID + "&"
            + SHOWAPI_API_SIGN + "=" + API_SIGN;

    /** @link {http://route.showapi.com/268-1?showapi_appid=62568
     * &showapi_sign=6cf04f645e6341f0ae8d882d13db6aff&SHOWAPI_NAME=&#x5317} */
    String SEARCH_BY_NAME = SHOWAPI_QUERY_MAIN + "&"+SHOWAPI_NAME + "=";

    /** used for get single value */
    String QUERY_BODY = "showapi_res_body";

    String QUERY_ID = "id";

    String QUERY_BODY_PAGE = "pagebean";
    String QUERY_BODY_PAGE_CONTENT_List = "contentlist";

    String CONTENT_LIST_SUMMARY = "summary";
    String CONTENT_LIST_PLACE_NAME = "name";// name of place
    String CONTENT_LIST_PRICE = "price";
    String CONTENT_LIST_OPEN_TIME = "opentime";

    String CONTENT_LIST_PORID = "proId"; // provinces
    String CONTENT_LIST_CITYID = "cityId"; // city
    String CONTENT_LIST_CITY_NAME = "cityName";
    String CONTENT_LIST_ADDRESS = "address";


    String CONTENT_LIST_PRICE_LIST = "priceList";

    /** for detail activity */
    String CONTENT_LIST_DISCOUNT = "coupon";       //discount
    String CONTENT_LIST_ATTENTION = "attention";   // attention
    String CONTENT_LIST_CONTENT = "content";       // content

    /** picture */
    String CONTENT_LIST_PIC_LIST = "picList";
    String CONTENT_LIST_PIC_LIST__URI = "picUrl";
    String CONTENT_LIST_PIC_LIST_SMALL_URI = "picUrlSmall";

    String LOCATION = "location";
    String LOCATION_LON = "lon";
    String LOCATION_LAT = "lat";

    String ERROR_SHOW = "NONE";

    interface SearchTAG{
        int FLAG_MODE_HOT = 0;
        int FLAG_MODE_HISTORY = 1;

        String SPILT_HOT_TAG = ";";
        String SPILT_HISTORY_TAG = "=";
    }

    interface IntentFlag {
        String FLAG_ARRAY_LIST_DETAIL = "flag_array_list_detail";

        String FLAG_FRAGMENT_MESSAGE = "flag_fragment_message";
        String FLAG_FRAGMENT_CONTENT = "flag_fragment_content";
        String FLAG_FRAGMENT_PICTURES = "flag_fragment_pictures";

        String FLAG_FRAGMENT_LOG = "fragment_cursor";

        String FLAG_PICTURE_LIST = "flag_picture_list";
        String FLAG_PICTURE_POSITION = "flag_picture_position";

        /**
         * SearchResultActivity get search keyword from intent.
         * This string used to transform data between SearchActivity and SearchResultActivity.
         */
        String FLAG_MODE_SEARCH_NAME = "search_name";
    }

    interface MapFlag {
        String FLAG_TIME_ARR = "flag_time_arraive";
        String FLAG_TIME_GO = "flag_time_go";
        String FLAG_EXISTS = "flag_exists";
    }

    interface OpenLocationMap{
        String COMPANY_NAME = "MilkDz";
        String APP_NAME = "WhereToTravel";

        String BAI_DU_MAP =
                "baidumap://map/navi?src=" + COMPANY_NAME + "|" + APP_NAME + "&location=";
        String BAI_DU_MAP_INFO = "&coord_type=gcj02&type=TIME";

        String GAO_DE_MAP = "androidamap://navi?sourceApplication=" + APP_NAME ;
        String GAO_DE_MAP_LON = "&lon=";
        String GAO_DE_MAP_LAT = "&lat=";
        String GAO_DE_MAP_INFO = "&dev=0&style=0";

        String GOOGLE_MAP = "http://ditu.google.cn/maps?hl=zh&mrt=loc&q=";
    }
}
