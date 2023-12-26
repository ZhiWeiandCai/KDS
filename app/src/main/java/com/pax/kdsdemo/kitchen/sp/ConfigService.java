package com.pax.kdsdemo.kitchen.sp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * File description
 *
 * @author yehongbo
 * @date 2022/11/29
 */
public class ConfigService {
    private static SharedPreferences sp;

    public static void init(Context context) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    @Nullable
    static String getString(@NonNull String key) {
        try {
            return getString(key, null);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    static String getString(@NonNull String key, @Nullable String defValue) {
        return sp.getString(key, defValue);
    }

    static void putString(@NonNull String key, @Nullable String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Nullable
    static Integer getInt(@NonNull String key) {
        try {
            String value = getString(key);
            if (value != null) {
                return Integer.parseInt(value);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    static int getInt(@NonNull String key, int defValue) {
        try {
            String value = getString(key);
            if (value != null) {
                return Integer.parseInt(value);
            }
            return defValue;
        } catch (Exception e) {
            return defValue;
        }
    }

    static void putInt(@NonNull String key, int value) {
        putString(key, Integer.toString(value));
    }

    @Nullable
    static Boolean getBoolean(@NonNull String key) {
        String value = getString(key);
        if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        } else {
            return null;
        }
    }

    static boolean getBoolean(@NonNull String key, boolean defValue) {
        String value = getString(key);
        if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        } else {
            return defValue;
        }
    }

    static void putBoolean(@NonNull String key, boolean value) {
        putString(key, Boolean.toString(value));
    }
}
