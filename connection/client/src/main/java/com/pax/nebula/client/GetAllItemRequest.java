package com.pax.nebula.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.pax.nebula.common.HttpConstants;
import com.pax.nebula.common.JsonUtils;
import com.pax.nebula.common.entity.GetAllItemResponse;
import okhttp3.Request;

/**
 * 获取所有订单项
 */
public class GetAllItemRequest {
    private final String serverIp;
    private final String tableId;

    private GetAllItemRequest(Builder builder) {
        this.serverIp = builder.serverIp;
        this.tableId = builder.tableId;
    }

    public void send(@NonNull OnResponseCallback<GetAllItemResponse> callback) {
        String url = HttpConstants.Order.GetAllItem.url(serverIp, tableId);
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpHelper.getInstance().send(request, new OnResponseCallback<String>() {
            @Override
            public void onResponse(@NonNull String response) {
                GetAllItemResponse data = JsonUtils.fromJson(response, GetAllItemResponse.class);
                if (data == null) {
                    callback.onError(new IllegalArgumentException("Cannot parse response"));
                } else {
                    callback.onResponse(data);
                }
            }

            @Override
            public void onError(@Nullable Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }

    public static class Builder {
        private String serverIp = "";
        private String tableId = "";

        public Builder setServerIp(@NonNull String serverIp) {
            this.serverIp = serverIp;
            return this;
        }

        public Builder setTableId(@NonNull String tableId) {
            this.tableId = tableId;
            return this;
        }

        public GetAllItemRequest build() {
            return new GetAllItemRequest(this);
        }
    }
}
