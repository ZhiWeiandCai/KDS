package com.pax.nebula.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.reflect.TypeToken;
import com.pax.nebula.common.HttpConstants;
import com.pax.nebula.common.JsonUtils;
import com.pax.nebula.common.entity.GetAllItemResponse;
import java.util.List;
import okhttp3.Request;

/**
 * File description
 *
 * @author yehongbo
 * @date 2023/2/7
 */
public class GetAllTableItemsRequest {
    private final String serverIp;

    private GetAllTableItemsRequest(Builder builder) {
        this.serverIp = builder.serverIp;
    }

    public void send(@NonNull OnResponseCallback<List<GetAllItemResponse>> callback) {
        String url = HttpConstants.Order.GetAllTableItems.url(serverIp);
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpHelper.getInstance().send(request, new OnResponseCallback<String>() {
            @Override
            public void onResponse(@NonNull String response) {
                List<GetAllItemResponse> data = JsonUtils.fromJson(response,
                        new TypeToken<List<GetAllItemResponse>>(){}.getType());
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
        private String serverIp;

        public Builder setServerIp(String serverIp) {
            this.serverIp = serverIp;
            return this;
        }

        public GetAllTableItemsRequest build() {
            return new GetAllTableItemsRequest(this);
        }
    }
}
