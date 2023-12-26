package com.pax.nebula.client;

import android.util.Log;
import androidx.annotation.NonNull;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * OkHttp 工具类
 */
class OkHttpHelper {
    private static final String TAG = "OkHttpHelper";

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(new RetryOneTimeInterceptor(1, 2000))
            .build();

    private OkHttpHelper() {
        // do nothing
    }

    private static class Holder {
        private static final OkHttpHelper INSTANCE = new OkHttpHelper();
    }

    static OkHttpHelper getInstance() {
        return Holder.INSTANCE;
    }

    void send(Request request, OnResponseCallback<String> responseCallback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Send failure", e);
                responseCallback.onError(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                ResponseBody body = response.body();
                if (body == null) {
                    Log.e(TAG, "Empty response body");
                    responseCallback.onError(new IllegalArgumentException("Empty response"));
                } else {
                    responseCallback.onResponse(body.string());
                }
            }
        });
    }
}
