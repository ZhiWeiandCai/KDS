package com.pax.nebula.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pax.nebula.common.HttpConstants;
import com.pax.nebula.common.JsonUtils;
import com.pax.nebula.common.entity.KDSDish;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 上传订单项
 */
public class PushKDSDishRequest {
    private final String serverIp;
    private final String deviceId;
    private final String tId;
    private final String tableId;
    private final List<KDSDish> items;

    private PushKDSDishRequest(Builder builder) {
        this.serverIp = builder.serverIp;
        this.deviceId = builder.deviceId;
        this.tId = builder.tId;
        this.tableId = builder.tableId;
        this.items = builder.items;
    }

    public void send(@NonNull OnResponseCallback<Boolean> callback) {
        String url = HttpConstants.Order.PushKDSDish.url(serverIp);
        RequestBody body = new FormBody.Builder()
                .add(HttpConstants.Order.PushKDSDish.param_deviceId, deviceId)
                .add(HttpConstants.Order.PushKDSDish.param_tId, tId)
                .add(HttpConstants.Order.PushKDSDish.param_tableId, tableId)
                .add(HttpConstants.Order.PushKDSDish.param_items, JsonUtils.toJson(items))
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
        private String tId = "";
        private String tableId = "";
        private List<KDSDish> items = new ArrayList<>();

        public Builder setServerIp(@NonNull String serverIp) {
            this.serverIp = serverIp;
            return this;
        }

        public Builder setDeviceId(@NonNull String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder setTId(@NonNull String tId) {
            this.tId = tId;
            return this;
        }

        public Builder setTableId(@NonNull String tableId) {
            this.tableId = tableId;
            return this;
        }

        public Builder setItems(@NonNull List<KDSDish> items) {
            this.items = items;
            return this;
        }

        public PushKDSDishRequest build() {
            return new PushKDSDishRequest(this);
        }
    }
}
