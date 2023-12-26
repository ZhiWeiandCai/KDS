package com.pax.nebula.client;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by caizhiwei on 2023/8/14
 */
public class RetryOneTimeInterceptor implements Interceptor {
    private final int maxRetries;
    private final int delayMillis;

    public RetryOneTimeInterceptor(int maxRetries, int delayMillis) {
        this.maxRetries = maxRetries;
        this.delayMillis = delayMillis;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        IOException lastException = null;

        for (int retry = 0; retry <= maxRetries; retry++) {
            try {
                response = chain.proceed(request);
                if (response.isSuccessful()) {
                    return response;
                }
            } catch (IOException e) {
                lastException = e;
            }

            if (retry < maxRetries) {
                try {
                    Thread.sleep(delayMillis); // 添加延迟
                } catch (InterruptedException ignored) {
                }
            }
        }

        throw lastException;
    }
}
