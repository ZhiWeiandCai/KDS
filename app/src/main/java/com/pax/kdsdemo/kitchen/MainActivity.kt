package com.pax.kdsdemo.kitchen

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.pax.kdsdemo.kitchen.utils.hideSystemBar
import com.pax.nebula.common.entity.GetAllItemResponse
import com.pax.nebula.common.entity.KDSDish
import com.pax.nebula.common.entity.OrderItem
import com.pax.nebula.server.IServer
import com.pax.nebula.server.ServerManager
import java.util.UUID

class MainActivity : AppCompatActivity(), IServer {
    companion object {
        val deviceId = UUID.randomUUID().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = uiOptions*/
        window.hideSystemBar()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainFragment>(R.id.fragment_container)
            }
        }
        ServerManager.getInstance().init(applicationContext, this)
        ServerManager.getInstance().start()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.i("MainActivity", "czw onKeyDown keyCode =$keyCode,")
        when (keyCode) {
            KeyEvent.KEYCODE_1 -> {

                return true
            }
            KeyEvent.KEYCODE_2 -> {

                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        ServerManager.getInstance().stop()
        super.onDestroy()
    }

    override fun useTable(deviceId: String?, tableId: String?): Boolean {
        return false
    }

    override fun getAllTableItems(): MutableList<GetAllItemResponse> {
        return ArrayList()
    }

    override fun getAllItem(tableId: String?): GetAllItemResponse {
        return GetAllItemResponse()
    }

    override fun pushAllItem(
        deviceId: String?,
        tableId: String?,
        personNum: String?,
        time: String?,
        items: MutableList<OrderItem>?
    ): Boolean {
        return false
    }

    override fun getIpConnectState(deviceId: String?, ip: String?): Boolean {
        return false
    }

    override fun pushKDSDish(
        deviceId: String?,
        tId: String?,
        tableId: String?,
        items: MutableList<KDSDish>?
    ): Boolean {
        Log.i("MainActivity", "pushKDSDish = $tableId")
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as MainFragment?
        if (tId != null && tableId != null && items != null) {
            fragment?.receiveDishes(tId, tableId, items)
        }
        return true
    }
}