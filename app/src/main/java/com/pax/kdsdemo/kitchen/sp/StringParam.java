package com.pax.kdsdemo.kitchen.sp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * File description
 *
 * @author yehongbo
 * @date 2022/11/29
 */
public class StringParam extends BaseParam<String> {
    public StringParam(@NonNull String key) {
        super(key);
    }

    @Override
    public void put(@NonNull String value) {
        ConfigService.putString(key, value);
    }

    @Nullable
    @Override
    public String get() {
        return ConfigService.getString(key);
    }

    @NonNull
    @Override
    public String get(@NonNull String defValue) {
        String value = ConfigService.getString(key, defValue);
        if (value == null) {
            return defValue;
        }
        return value;
    }
}
