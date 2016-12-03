package com.woofnroof.woofnroofapp;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by jbarron on 11/22/16.
 */

public class WoofnRoofApi {

    private static final String BASE_URL = "http://www.woofnroof.com";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, RequestParams params, String tokenWoof, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Authorization","Bearer "+tokenWoof);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params,String tokenWoof, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Authorization","Bearer "+tokenWoof);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void put(String url, RequestParams params,String tokenWoof, AsyncHttpResponseHandler responseHandler){
        client.addHeader("Authorization","Bearer "+tokenWoof);
        client.put(getAbsoluteUrl(url),params,responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


}
