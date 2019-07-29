package com.yourdiscovery.discovery.main.network;

import com.yourdiscovery.discovery.GlobalConst;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user1 on 2/15/2017.
 */
public class APIManager {

    private static Retrofit retrofit = null;
    private static APIInterface apiInterface = null;

    public static APIInterface getClient() {
        if (retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalConst.URL_APIServer)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if(apiInterface == null){
            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }

    public static void reqLogin(String username,String password, final ApiCallBack aCallBack){
        Map<String,String> _param = new HashMap<String,String>();
        _param.put("user_name",username);
        _param.put("password",password);

        Call<ApiResponse> _response = APIManager.getClient().apiLogin(_param);
        _response.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                aCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                aCallBack.onFailure();
            }
        });
    }

    public static void reqSearchClient(String userID,String userKey,String fullName,String email, final ApiCallBack aCallBack){
        Map<String,String> _param = new HashMap<String,String>();
        _param.put("id",userID);
        _param.put("userKey",userKey);
        if(fullName != null)
            _param.put("name",fullName);
        if(email != null)
            _param.put("email",email);

        Call<SearchResponse> _response = APIManager.getClient().apiSearchClient(_param);
        _response.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response.body() == null)
                    aCallBack.onFailure();
                else
                    aCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                aCallBack.onFailure();
            }
        });
    }

    public static void reqRegisterResult(String userID,String userKey,String name, String email, String result, String feedback, final ApiCallBack aCallBack){
        Map<String,String> _param = new HashMap<String,String>();
        _param.put("id",userID);
        _param.put("userKey",userKey);
        _param.put("name",name);
        _param.put("email",email);
        _param.put("result",result);
        _param.put("feedback",feedback);

        Call<ApiResponse> _response = APIManager.getClient().apiRegisterResult(_param);
        _response.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                aCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                aCallBack.onFailure();
            }
        });
    }


}
