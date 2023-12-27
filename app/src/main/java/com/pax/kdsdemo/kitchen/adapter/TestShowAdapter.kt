package com.pax.kdsdemo.kitchen.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pax.kdsdemo.kitchen.MainViewModel
import com.pax.kdsdemo.kitchen.R
import com.pax.kdsdemo.kitchen.data.Constants
import com.pax.kdsdemo.kitchen.data.TableDish
import com.pax.kdsdemo.kitchen.databinding.ItemTestShowBinding
import com.pax.kdsdemo.kitchen.utils.ButtonUtils

/**
 * Created by caizhiwei on 2023/7/26
 */
class TestShowAdapter(private val viewModel: MainViewModel) : ListAdapter<TableDish, TestShowViewHolder>(TestShowDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestShowViewHolder {
        val binding = ItemTestShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //val binding = ItemTestShowBinding.inflate(LayoutInflater.from(parent.context))
        return TestShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TestShowViewHolder, position: Int) {
        val item = getItem(position)
        if (item.status == 1) {
            (holder.itemView as CardView).setCardBackgroundColor(Constants.COLOR_G2)
            holder.oStatusTv.text = holder.itemView.context.getText(R.string.order_ready)
            holder.key.background = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.bg_pb_press_key)
        } else if (item.status == 0) {
            (holder.itemView as CardView).setCardBackgroundColor(Constants.COLOR_B2)
            holder.oStatusTv.text = holder.itemView.context.getText(R.string.order_pending)
            holder.key.background = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.bg_pb_press_key_p)
        }
        holder.textView.text = item.tableName
        holder.key.text = item.key.toString()
        Log.i("czw", "onBindViewHolder-" + item.key)
        val innerAdapterDish = DishAdapter()
        holder.rw.adapter = innerAdapterDish
        innerAdapterDish.submitList(item.dishes)
        holder.rw.setOnTouchListener { v, event ->
            return@setOnTouchListener holder.itemView.onTouchEvent(event)
        }
        holder.itemView.setOnClickListener {
            if (ButtonUtils.isFastDoubleClick()) {
                 return@setOnClickListener
            }
            if (item.status == 0) updateCardStatus(item, 1)
            else if (item.status == 1) deleteItem(item)
        }
    }

    private fun updateCardStatus(item: TableDish, status: Int) {
        viewModel.updateCardStatus(item, status)
    }

    private fun deleteItem(item: TableDish) {
        viewModel.deleteTableDishItem(item)
    }
}

class TestShowViewHolder(binding: ItemTestShowBinding) : RecyclerView.ViewHolder(binding.root) {
    val textView: TextView = binding.textViewItemShow
    val oStatusTv: TextView = binding.textViewOStatus
    val key: TextView = binding.textViewPbKey
    val rw: RecyclerView = binding.rvDish
    val complete: MaterialButton = binding.btnComplete
}

private class TestShowDiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<TableDish>() {
    override fun areItemsTheSame(oldItem: TableDish, newItem: TableDish): Boolean {
        Log.i("czw", "areItemsTheSame-o_id=" + oldItem.id + " newId=" + newItem.id + " o_key=" + oldItem.key + " n_key=" + newItem.key)
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TableDish, newItem: TableDish): Boolean {
        Log.i("czw", "areContentsTheSame-o_id=" + oldItem.tableName + " n_id=" + newItem.tableName
                + " o_key=" + oldItem.key + " n_key=" + newItem.key + " o_s=" + oldItem.status + " n_s=" + newItem.status)
        return oldItem.id == newItem.id && oldItem.key == newItem.key && oldItem.status == newItem.status
    }
}