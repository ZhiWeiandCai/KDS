package com.pax.kdsdemo.kitchen

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.pax.kdsdemo.kitchen.data.Constants
import com.pax.kdsdemo.kitchen.utils.ButtonUtils
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
    private var orderNum: Int = 1000

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
        if (ButtonUtils.isFastDoubleClick()) {
            return true
        }
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as MainFragment?
        if (fragment != null) {
            if (fragment.isResumed)
                when (keyCode) {
                    KeyEvent.KEYCODE_7 -> {
                        fragment.pageLeft()
                        return true
                    }

                    KeyEvent.KEYCODE_8 -> {
                        fragment.pageRight()
                        return true
                    }

                    KeyEvent.KEYCODE_1 -> {
                        fragment.clickOnPb20(1)
                        return true
                    }

                    KeyEvent.KEYCODE_2 -> {
                        fragment.clickOnPb20(2)
                        return true
                    }

                    KeyEvent.KEYCODE_3 -> {
                        fragment.clickOnPb20(3)
                        return true
                    }

                    KeyEvent.KEYCODE_4 -> {
                        fragment.clickOnPb20(4)
                        return true
                    }

                    KeyEvent.KEYCODE_5 -> {
                        fragment.clickOnPb20(5)
                        return true
                    }

                    KeyEvent.KEYCODE_6 -> {
                        fragment.clickOnPb20(6)
                        return true
                    }

                    KeyEvent.KEYCODE_A -> {
                        fragment.clickOnPb20(7)
                        return true
                    }

                    KeyEvent.KEYCODE_B -> {
                        fragment.clickOnPb20(8)
                        return true
                    }

                    KeyEvent.KEYCODE_C -> {
                        fragment.clickOnPb20(9)
                        return true
                    }

                    KeyEvent.KEYCODE_D -> {
                        fragment.clickOnPb20(10)
                        return true
                    }

                    KeyEvent.KEYCODE_E -> {
                        fragment.clickOnPb20(11)
                        return true
                    }

                    KeyEvent.KEYCODE_F -> {
                        fragment.clickOnPb20(12)
                        return true
                    }

                    KeyEvent.KEYCODE_G -> {
                        fragment.showCustomDialog()
                        return true
                    }
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
            fragment?.receiveDishes((++orderNum).toString(), Constants.ORDER_WORD_C + orderNum, items)
        }
        return true
    }
}