package com.milkzs.android.wheretotravel.Tool;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.milkzs.android.wheretotravel.Base.BaseInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by milkdz on 2018/4/21.
 */

public class DataRequest {

    private static String TAG = "DataRequest";

    /**
     * build url for query
     *
     * @return query url
     */
    public static URL buildUriForShowApi(){
        Uri buildUri = Uri.parse(BaseInfo.SHOWAPI_QUERY_MAIN);
        Log.i(TAG,"query uri is " + buildUri);
        try {
            URL url = new URL(buildUri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * search json data by scene name
     *
     * @param name scene name entered by user
     * @return response uri
     */
    public static URL buildURIForSearchKeyword(String name){
        Uri buildUri = Uri.parse(BaseInfo.SEARCH_BY_NAME + name);
        Log.i(TAG,"query uri is " + buildUri);
        try {
            URL url = new URL(buildUri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    @Nullable
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(10000);
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
