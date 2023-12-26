package com.pax.kdsdemo.kitchen.data

/**
 * Created by caizhiwei on 2023/7/27
 */
data class TableDish(val id: Int, val tableName: String, val dishes: List<DishItem>, var status: Int, var key: Int)

data class DishItem(val id: Int, val name: String, val quantity: Int)
