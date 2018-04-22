package com.milkzs.android.wheretotravel.Base;

/**
 * Created by milkdz on 2018/4/21.
 */

public interface BaseInfo {

    /** base param for query */
    String BASE_URI = "http://route.showapi.com/268-1";
    String SHOWAPI_API_ID = "showapi_appid";
    String API_ID = "62568"; // <----  api id
    String SHOWAPI_API_SIGN = "showapi_sign";
    String API_SIGN = "6cf04f645e6341f0ae8d882d13db6aff";// <---- api sign

    /** http://route.showapi.com/268-1?showapi_appid= &showapi_sign=  */
    String SHOWAPI_QUERY_MAIN = BASE_URI + "?"
            + SHOWAPI_API_ID + "=" + API_ID + "&"
            + SHOWAPI_API_SIGN + "=" + API_SIGN;

    /** used for get single value */
    String QUERY_BODY = "showapi_res_body";

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
    String CONTENT_LIST_CONTENT = "content"; // content


    String CONTENT_LIST_PIC_LIST = "picList"; //pic
}