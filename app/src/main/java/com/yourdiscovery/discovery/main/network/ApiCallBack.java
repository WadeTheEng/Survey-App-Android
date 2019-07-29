package com.yourdiscovery.discovery.main.network;

/**
 * Created by user1 on 2/20/2017.
 */
public interface ApiCallBack {
    void onSuccess(ApiResponse response);
    void onFailure();
}
