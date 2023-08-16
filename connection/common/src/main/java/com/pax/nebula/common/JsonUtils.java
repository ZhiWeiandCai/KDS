package com.pax.nebula.common;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import java.lang.reflect.Type;

/**
 * File description
 *
 * @author yehongbo
 * @date 2023/1/10
 */
public class JsonUtils {
    private static final String TAG = "JsonUtils";
    private static final Gson gson = new Gson();

    private JsonUtils() {
        // do nothing
    }

    @NonNull
    public static String toJson(@Nullable Object data) {
        if (data == null) {
            return "";
        }
        try {
            String jsonData = gson.toJson(data);
            if (jsonData == null) {
                return "";
            } else {
                return jsonData;
            }
        } catch (Exception e) {
            Log.e(TAG, "Convert to json failed", e);
            return "";
        }
    }

    @Nullable
    public static <T> T fromJson(@Nullable String json, @Nullable Class<T> type) {
        if (json == null || type == null) {
            return null;
        }
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            Log.e(TAG, "Convert from json failed", e);
            return null;
        }
    }

    @Nullable
    public static <T> T fromJson(@Nullable String json, @Nullable Type type) {
        if (json == null || type == null) {
            return null;
        }
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            Log.e(TAG, "Convert from json failed", e);
            return null;
        }
    }
}
