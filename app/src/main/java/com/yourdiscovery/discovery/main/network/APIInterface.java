package com.yourdiscovery.discovery.main.network;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by user1 on 2/15/2017.
 */
public interface APIInterface {

    @FormUrlEncoded
    @POST("login")
    Call<ApiResponse> apiLogin(@FieldMap Map<String, String> names);

    @FormUrlEncoded
    @POST("searchClient")
    Call<SearchResponse> apiSearchClient(@FieldMap Map<String, String> names);

    @FormUrlEncoded
    @POST("registerResult")
    Call<ApiResponse> apiRegisterResult(@FieldMap Map<String, String> names);

}
