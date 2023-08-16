package com.pax.nebula.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pax.nebula.common.HttpConstants;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by caizhiwei on 2023/8/2
 */
public class IPConnectRequest {
    private final String serverIp;
    private final String deviceId;
    private final String ip;

    private IPConnectRequest(Builder builder) {
        this.serverIp = builder.serverIp;
        this.deviceId = builder.deviceId;
        this.ip = builder.ip;
    }

    public void send(@NonNull OnResponseCallback<Boolean> callback) {
        String url = HttpConstants.Order.GetIPConnectState.url(serverIp);
        RequestBody body = new FormBody.Builder()
                .add(HttpConstants.Order.GetIPConnectState.param_deviceId, deviceId)
                .add(HttpConstants.Order.GetIPConnectState.param_ip, ip)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OkHttpHelper.getInstance().send(request, new OnResponseCallback<String>() {
            @Override
            public void onResponse(@NonNull String response) {
                callback.onResponse(Boolean.parseBoolean(response));
            }

            @Override
            public void onError(@Nullable Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }

    public static class Builder {
        private String serverIp = "";
        private String deviceId = "";
        private String ip = "";

        public Builder setServerIp(@NonNull String serverIp) {
            this.serverIp = serverIp;
            return this;
        }

        public Builder setDeviceId(@NonNull String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder setIp(@NonNull String ip) {
            this.ip = ip;
            return this;
        }

        public IPConnectRequest build() {
            return new IPConnectRequest(this);
        }
    }
}
