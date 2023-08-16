package com.pax.nebula.server;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 服务器逻辑接口
 */
public interface IServer extends IOrder {
    default void onStarted() {
        Log.d("HttpServer", "Server started");
    }

    default void onStopped() {
        Log.d("HttpServer", "Server stopped");
    }

    default void onException(@NonNull String message, @Nullable Throwable throwable) {
        Log.e("HttpServer", message, throwable);
    }
}
