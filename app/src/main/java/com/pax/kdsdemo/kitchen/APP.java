package com.pax.kdsdemo.kitchen;

import android.app.Application;

import com.pax.kdsdemo.kitchen.sp.ConfigService;

/**
 * Created by caizhiwei on 2023/12/26
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ConfigService.init(this);
    }
}
