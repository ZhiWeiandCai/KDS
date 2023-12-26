package com.pax.kdsdemo.kitchen.sp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * File description
 *
 * @author yehongbo
 * @date 2022/11/29
 */
abstract class BaseParam<T> {
    protected final String key;

    protected BaseParam(@NonNull String key) {
        this.key = key;
    }

    public abstract void put(@NonNull T value);

    @Nullable
    public abstract T get();

    @NonNull
    public abstract T get(@NonNull T defValue);

    @NonNull
    public String key() {
        return key;
    }
}
