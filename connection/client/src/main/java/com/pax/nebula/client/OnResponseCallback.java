package com.pax.nebula.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 响应回调
 */
public interface OnResponseCallback<T> {
    /**
     * 正常响应
     * @param response 响应结果
     */
    void onResponse(@NonNull T response);

    /**
     * 异常
     */
    void onError(@Nullable Throwable throwable);
}
