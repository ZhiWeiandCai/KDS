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
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pax.kdsdemo.kitchen.adapter.TestShowAdapter
import com.pax.kdsdemo.kitchen.databinding.FragmentMainBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerViewShow = binding.rvShow
        //recyclerViewShow.addItemDecoration(SpaceItemDecorationShow(60))
        val adapterShow = TestShowAdapter(viewModel)
        recyclerViewShow.adapter = adapterShow
        viewModel.texts.observe(viewLifecycleOwner) {
            adapterShow.submitList(it)
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
                binding.btConnect.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.orange_btn_tint)
                binding.btConnect.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_connect, null)
            } else {
                binding.btConnect.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.white)
                binding.btConnect.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_connect_dis, null)
            }
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

    private fun showCustomDialog() {
        val customView = layoutInflater.inflate(R.layout.dialog_input_ip, null)
        val alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(customView).create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.window?.setLayout(986, 480)
        alertDialog.window?.hideSystemBar()
        val et = customView.findViewById<EditText>(R.id.et_ip)
        if (!TextUtils.isEmpty(viewModel.ipServer.value))
            et.setText(viewModel.ipServer.value)
        // 获取自定义按钮，并添加点击事件处理
        val btnPositive = customView.findViewById<Button>(R.id.btnPositive)
        val btnNegative = customView.findViewById<Button>(R.id.btnNegative)
        btnPositive.setOnClickListener {
            // 处理 Positive 按钮点击事件
            val ip = et.text.toString()
            viewModel.setIpServer(ip)
            alertDialog.dismiss()
        }
        btnNegative.setOnClickListener {
            // 处理 Negative 按钮点击事件
            alertDialog.dismiss()
        }
        alertDialog.show()
    }


}