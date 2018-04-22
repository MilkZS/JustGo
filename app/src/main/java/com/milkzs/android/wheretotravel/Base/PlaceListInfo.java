package com.milkzs.android.wheretotravel.Base;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/4/21.
 */

public class PlaceListInfo {

    private String placeName ;
    private String summary ;
    private String address;
    private String openTime;
    private String price;
    private Uri mainPicUri;

    /** pictures */
    private ArrayList<Uri> picListUrl ;
    private ArrayList<Uri> picListSmallUrl;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Uri getMainPicUri() {
        return mainPicUri;
    }

    public void setMainPicUri(Uri mainPicUri) {
        this.mainPicUri = mainPicUri;
    }

    public ArrayList<Uri> getPicListUrl() {
        return picListUrl;
    }

    public void setPicListUrl(ArrayList<Uri> picListUrl) {
        this.picListUrl = picListUrl;
    }

    public ArrayList<Uri> getPicListSmallUrl() {
        return picListSmallUrl;
    }

    public void setPicListSmallUrl(ArrayList<Uri> picListSmallUrl) {
        this.picListSmallUrl = picListSmallUrl;
    }
}
