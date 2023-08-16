package com.pax.nebula.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.pax.nebula.common.HttpConstants;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * File description
 *
 * @author yehongbo
 * @date 2023/2/7
 */
public class CancelTableRequest {
    private final String serverIp;
    private final String deviceId;
    private final String tableId;

    private CancelTableRequest(Builder builder) {
        this.serverIp = builder.serverIp;
        this.deviceId = builder.deviceId;
        this.tableId = builder.tableId;
    }

    public void send(@NonNull OnResponseCallback<Boolean> callback) {
        String url = HttpConstants.Order.CancelTable.url(serverIp);
        RequestBody body = new FormBody.Builder()
                .add(HttpConstants.Order.CancelTable.param_deviceId, deviceId)
                .add(HttpConstants.Order.CancelTable.param_tableId, tableId)
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
        private String tableId = "";

        public Builder setServerIp(@NonNull String serverIp) {
            this.serverIp = serverIp;
            return this;
        }

        public Builder setDeviceId(@NonNull String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder setTableId(@NonNull String tableId) {
            this.tableId = tableId;
            return this;
        }

        public CancelTableRequest build() {
            return new CancelTableRequest(this);
        }
    }
}
