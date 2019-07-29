package com.yourdiscovery.discovery.main.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user1 on 2/20/2017.
 */
public class SearchResponse extends ApiResponse {

    @SerializedName("type")
    public int  nType;

    public SearchResponse(int id, int success, String error,int type){
        super(id,success,error,"");
        this.nType = type;
    }
}
