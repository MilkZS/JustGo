package com.milkzs.android.wheretotravel.Base;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by milkdz on 2018/4/21.
 */

public class PlaceListInfo implements Parcelable{

    private String sId;

    private String placeName ;
    private String summary ;
    private String address;
    private String openTime;
    private String price;
    private Uri mainPicUri;

    private String discount;
    private String attention;
    private String detailContent;

    private String location_lon;
    private String location_lat;

    /** pictures */
    private ArrayList picListUrl ;
    private ArrayList picListSmallUrl;

    private HashMap logMap;

    public PlaceListInfo() {
    }

    public void setLogMap(HashMap<String, String> logMap) {
        this.logMap = logMap;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getLocation_lon() {
        return location_lon;
    }

    public void setLocation_lon(String location_lon) {
        this.location_lon = location_lon;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

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

    public void setPicListUrl(ArrayList<Uri> picListUrl) {
        this.picListUrl = picListUrl;
    }

    public ArrayList<Uri> getPicListSmallUrl() {
        return picListSmallUrl;
    }

    public void setPicListSmallUrl(ArrayList<Uri> picListSmallUrl) {
        this.picListSmallUrl = picListSmallUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeName);
        dest.writeString(summary);
        dest.writeString(address);
        dest.writeString(openTime);
        dest.writeString(price);
        dest.writeList(picListUrl);
        dest.writeList(picListSmallUrl);
        dest.writeValue(mainPicUri);

        dest.writeString(discount);
        dest.writeString(attention);
        dest.writeString(detailContent);

        dest.writeString(location_lon);
        dest.writeString(location_lat);

        dest.writeString(sId);

        dest.writeMap(logMap);
    }

    public final static Parcelable.Creator<PlaceListInfo> CREATOR = new Creator<PlaceListInfo>() {
        @Override
        public PlaceListInfo createFromParcel(Parcel source) {
            return new PlaceListInfo(source);
        }

        @Override
        public PlaceListInfo[] newArray(int size) {
            return new PlaceListInfo[size];
        }
    };

    private PlaceListInfo(Parcel parcel) {
        placeName = parcel.readString();
        summary = parcel.readString();
        address = parcel.readString();
        openTime = parcel.readString();
        price = parcel.readString();
        picListUrl = parcel.readArrayList(ArrayList.class.getClassLoader());
        picListSmallUrl = parcel.readArrayList(ArrayList.class.getClassLoader());
        mainPicUri = (Uri) parcel.readValue(Uri.class.getClassLoader());

        discount = parcel.readString();
        attention = parcel.readString();
        detailContent = parcel.readString();

        location_lon = parcel.readString();
        location_lat = parcel.readString();

        sId = parcel.readString();

        logMap = parcel.readHashMap(HashMap.class.getClassLoader());
    }
}
