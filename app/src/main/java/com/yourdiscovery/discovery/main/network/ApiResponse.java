package com.yourdiscovery.discovery.main.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user1 on 2/20/2017.
 */
public class ApiResponse {

    @SerializedName("id")
    public int nId;

    @SerializedName("success")
    public int bSuccess;

    @SerializedName("error")
    public String strError;

    @SerializedName("userKey")
    public String strUserKey;

    ApiResponse(){

    }

    public ApiResponse(int id, int success, String error, String userKey) {
        this.nId = id;
        this.bSuccess = success;
        this.strError = error;
        this.strUserKey = userKey;
    }

}
