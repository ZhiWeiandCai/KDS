package com.pax.kdsdemo.kitchen.utils

import android.view.Window
import androidx.annotation.MainThread
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * 隐藏状态栏与导航栏
 */
@MainThread
fun Window.hideSystemBar() {
    WindowInsetsControllerCompat(this, decorView).also {
        it.hide(WindowInsetsCompat.Type.statusBars())
        it.hide(WindowInsetsCompat.Type.navigationBars())
    }
}

/**
 * 显示状态栏与导航栏
 */
@MainThread
fun Window.showSystemBar() {
    WindowInsetsControllerCompat(this, decorView).also {
        it.hide(WindowInsetsCompat.Type.statusBars())
        it.hide(WindowInsetsCompat.Type.navigationBars())
    }
}

/**
 * 是否允许通过滑动来显示状态栏与导航栏
 */
@MainThread
fun Window.isAllowSwipeToShowSystemBar(isAllow: Boolean) {
    WindowInsetsControllerCompat(this, decorView).also {
        it.systemBarsBehavior = if (isAllow) {
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
        }
    }
}