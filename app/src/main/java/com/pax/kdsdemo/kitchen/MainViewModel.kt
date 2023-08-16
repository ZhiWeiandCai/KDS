package com.pax.kdsdemo.kitchen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.NetworkUtils
import com.pax.kdsdemo.kitchen.data.DishItem
import com.pax.kdsdemo.kitchen.data.TableDish
import com.pax.kdsdemo.kitchen.utils.RegexUtils
import com.pax.nebula.client.IPConnectRequest
import com.pax.nebula.client.OnResponseCallback
import com.pax.nebula.common.entity.KDSDish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Date

class MainViewModel : ViewModel() {

    private var isStart: Boolean = false

    init {
        startTask()
    }

    /*private val _texts = MutableLiveData<List<TableDish>>().apply {
        value = (1..16).mapIndexed { _, i ->
            TableDish(i,"Table $i", listOf(
                DishItem(i, "dish $i", 2),
                DishItem(i, "dish $i", 2),
                DishItem(i, "dish $i", 2),
                DishItem(i, "dish $i", 2),
                DishItem(i, "dish $i", 2),
                DishItem(i, "dish $i", 2)
            ))
        }
    }*/
    private val _texts = MutableLiveData<List<TableDish>>()

    val texts: LiveData<List<TableDish>> = _texts

    private val _isConnect = MutableLiveData(false)

    val isConnect: LiveData<Boolean> = _isConnect

    private val _ipServer = MutableLiveData<String>()

    val ipServer: LiveData<String> = _ipServer

    fun setIpServer(ip: String) {
        _ipServer.value = ip
        startTaskOnce()
        isStart = true
    }

    private fun startTask() {
        // 创建一个协程，并使用定时器定期执行任务
        viewModelScope.launch(Dispatchers.IO) {
            // 任务的执行间隔，这里设置为16秒
            val intervalInMillis = 21000L

            // 循环执行任务，直到协程被取消（即 ViewModel 被清理或销毁）
            while (isActive) {
                // 执行你的任务
                if (isStart) executeTask()

                // 等待一段时间，模拟定时器的间隔
                delay(intervalInMillis)
            }
        }
    }

    private fun executeTask() {
        // 这里可以放你的任务逻辑
        println("Task executed at ${Date()}")
        val ip = NetworkUtils.getIPAddress(true)
        requestConnectState(ip)
    }

    private fun requestConnectState(ip: String) {
        val isValidLocal = RegexUtils.Constants.isValidIpAddress(ip)
        if (isValidLocal) {
            val isValid = ipServer.value?.let { RegexUtils.Constants.isValidIpAddress(it) }
            if (isValid == true) {
                ipServer.value?.let {
                    IPConnectRequest.Builder()
                        .setServerIp(it)
                        .setDeviceId(MainActivity.deviceId)
                        .setIp(ip)
                        .build().send(object : OnResponseCallback<Boolean?> {
                            override fun onResponse(response: Boolean) {
                                Log.d("requestConnectState", "requestConnectState response: $response")
                                _isConnect.postValue(response)
                            }

                            override fun onError(throwable: Throwable?) {
                                Log.d("requestConnectState", "Error", throwable)
                                _isConnect.postValue(false)
                            }
                        })
                }
            } else {
                _isConnect.postValue(false)
            }
        } else {
            _isConnect.postValue(false)
        }
    }

    private fun startTaskOnce() {
        viewModelScope.launch(Dispatchers.IO) {
            executeTask()
        }
    }

    fun updateTableDish(tId: String, tableName: String, items: MutableList<KDSDish>) {
        val dish: ArrayList<DishItem> = ArrayList()
        for (item in items) {
            dish.add(DishItem(item.id.toInt(), item.name, item.num))
        }
        val tableDish = TableDish(tId.toInt(), tableName, dish)
        val foundItem = _texts.value?.find { it.id == tableDish.id }
        if (foundItem == null) {
            // 获取当前的列表
            val currentList = _texts.value?.toMutableList() ?: mutableListOf()
            currentList.add(tableDish)
            _texts.postValue(currentList)
        }
    }

    fun deleteTableDishItem(position: TableDish) {
        // 获取当前的列表
        val currentList = _texts.value?.toMutableList() ?: mutableListOf()
        currentList.remove(position)
        _texts.value = currentList
    }
}