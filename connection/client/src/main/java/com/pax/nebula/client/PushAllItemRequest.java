package com.pax.nebula.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.pax.nebula.common.HttpConstants;
import com.pax.nebula.common.JsonUtils;
import com.pax.nebula.common.entity.OrderItem;
import java.util.ArrayList;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 上传订单项
 */
public class PushAllItemRequest {
    private final String serverIp;
    private final String deviceId;
    private final String tableId;
    private final String personNum;
    private final String time;
    private final List<OrderItem> items;

    private PushAllItemRequest(Builder builder) {
        this.serverIp = builder.serverIp;
        this.deviceId = builder.deviceId;
        this.tableId = builder.tableId;
        this.personNum = builder.personNum;
        this.time = builder.time;
        this.items = builder.items;
    }

    public void send(@NonNull OnResponseCallback<Boolean> callback) {
        String url = HttpConstants.Order.PushAllItem.url(serverIp);
        RequestBody body = new FormBody.Builder()
                .add(HttpConstants.Order.PushAllItem.param_deviceId, deviceId)
                .add(HttpConstants.Order.PushAllItem.param_tableId, tableId)
                .add(HttpConstants.Order.PushAllItem.param_personNum, personNum)
                .add(HttpConstants.Order.PushAllItem.param_time, time)
                .add(HttpConstants.Order.PushAllItem.param_items, JsonUtils.toJson(items))
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
        private String personNum = "";
        private String time = "";
        private List<OrderItem> items = new ArrayList<>();

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

        public Builder setPersonNum(String personNum) {
            this.personNum = personNum;
            return this;
        }

        public Builder setTime(String time) {
            this.time = time;
            return this;
        }

        public Builder setItems(@NonNull List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public PushAllItemRequest build() {
            return new PushAllItemRequest(this);
        }
    }
}
