package com.pax.kdsdemo.kitchen

import android.media.MediaPlayer
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pax.kdsdemo.kitchen.adapter.TestShowAdapter
import com.pax.kdsdemo.kitchen.databinding.FragmentMainBinding
import com.pax.kdsdemo.kitchen.sp.StringParam
import com.pax.kdsdemo.kitchen.ui.SpaceItemDecorationShow
import com.pax.kdsdemo.kitchen.utils.ButtonUtils
import com.pax.kdsdemo.kitchen.utils.RegexUtils
import com.pax.kdsdemo.kitchen.utils.hideSystemBar
import com.pax.nebula.common.entity.KDSDish

class MainFragment : Fragment() {
    companion object {
        private const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private var mMediaPlayer: MediaPlayer? = null
    //要连接的服务器的IP地址
    private val wifiAddress: StringParam = StringParam("WIFI_ADDRESS")
    private lateinit var recyclerViewShow: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerViewShow = binding.rvShow
        recyclerViewShow.addItemDecoration(SpaceItemDecorationShow(30))
        val gridLayoutManager = GridLayoutManager(requireContext(), 4)
        recyclerViewShow.layoutManager = gridLayoutManager
        recyclerViewShow.isNestedScrollingEnabled = false
        val adapterShow = TestShowAdapter(viewModel)
        recyclerViewShow.adapter = adapterShow
        viewModel.texts.observe(viewLifecycleOwner) {
            adapterShow.submitList(it)
            if (viewModel.pageTotalChange) {
                binding.indicator.createIndicators(viewModel.pageSizeCallback(), viewModel.pageCountCallback())
                viewModel.pageTotalChange = false
            }
            if (it.isNotEmpty()) {
                binding.indicator.visibility = View.VISIBLE
                binding.indicator.animatePageSelected(viewModel.pageCountCallback())
            } else {
                binding.indicator.visibility = View.INVISIBLE
            }
        }
        binding.btConnect.setOnClickListener {
            showCustomDialog()
        }
        viewModel.ipServer.observe(viewLifecycleOwner) {
            Log.i(TAG, "请求连接服务端=$it")
            val isValid = RegexUtils.Constants.isValidIpAddress(it)
            println("Is valid IP address? $isValid")
        }
        viewModel.isConnect.observe(viewLifecycleOwner) {
            Log.i(TAG, "isConnect=$it")
            if (it) {
                binding.btConnect.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.green_2)
                binding.btConnect.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_connect, null)
                binding.btConnect.setText(R.string.w_wifi_connected)
            } else {
                binding.btConnect.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.white)
                binding.btConnect.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_connect_dis, null)
                binding.btConnect.setText(R.string.w_wifi_disconnected)
            }
        }
        binding.btnPageLeft.setOnClickListener {
            if (ButtonUtils.isFastDoubleClick()) return@setOnClickListener
            viewModel.pageLeftAction()
        }
        binding.btnPageRight.setOnClickListener {
            if (ButtonUtils.isFastDoubleClick()) return@setOnClickListener
            viewModel.pageRightAction()
        }
        binding.indicator.createIndicators(1, 0)
        binding.indicator.visibility = View.INVISIBLE
        if (!TextUtils.isEmpty(wifiAddress.get())) {
            wifiAddress.get()?.let { viewModel.setIpServer(it) }
        }
        return root
    }

    fun receiveDishes(tId: String, tableName: String, items: MutableList<KDSDish>) {
        viewModel.updateTableDish(tId, tableName, items)
        if (mMediaPlayer == null) mMediaPlayer = MediaPlayer.create(requireContext(), R.raw.ok)
        mMediaPlayer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMediaPlayer?.release()
        mMediaPlayer = null
        _binding = null
    }

    fun clickOnPb20(key: Int) {
        Log.i(TAG, "clickOnPb20 key=$key rv.childCount=" +recyclerViewShow.childCount)
        if (recyclerViewShow.childCount >= key) {
            ButtonUtils.resetLastClickTime()
            recyclerViewShow.getChildAt(key - 1).performClick()
        }
    }

    fun pageLeft() {
        viewModel.pageLeftAction()
    }

    fun pageRight() {
        viewModel.pageRightAction()
    }

    private var alertDialog: AlertDialog? = null
    fun showCustomDialog() {
        if (alertDialog?.isShowing == true) return
        val customView = layoutInflater.inflate(R.layout.dialog_input_ip, null)
        alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(customView).create()
        alertDialog!!.setCanceledOnTouchOutside(false)
        alertDialog!!.window?.setLayout(986, 480)
        alertDialog!!.window?.hideSystemBar()
        val et = customView.findViewById<EditText>(R.id.et_ip)
        if (!TextUtils.isEmpty(viewModel.ipServer.value))
            et.setText(viewModel.ipServer.value)
        // 获取自定义按钮，并添加点击事件处理
        val btnPositive = customView.findViewById<Button>(R.id.btnPositive)
        val btnNegative = customView.findViewById<Button>(R.id.btnNegative)
        btnPositive.setOnClickListener {
            // 处理 Positive 按钮点击事件
            val ip = et.text.toString()
            if (TextUtils.isEmpty(ip)) {
                wifiAddress.put("")
            } else if (RegexUtils.Constants.isValidIpAddress(ip)) {
                wifiAddress.put(ip)
            }
            viewModel.setIpServer(ip)
            alertDialog!!.dismiss()
        }
        btnNegative.setOnClickListener {
            // 处理 Negative 按钮点击事件
            alertDialog!!.dismiss()
        }
        alertDialog!!.show()
    }

}