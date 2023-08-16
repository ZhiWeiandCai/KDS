package com.pax.kdsdemo.kitchen.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by caizhiwei on 2023/8/1
 */
class SpaceItemDecorationShow(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //不是第一个的格子都设一个顶部的间距
        //outRect.left = space
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        /*if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = 2
        }*/
    }
}