package com.pax.kdsdemo.kitchen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pax.kdsdemo.kitchen.data.DishItem
import com.pax.kdsdemo.kitchen.databinding.ItemDishNumBinding

/**
 * Created by caizhiwei on 2023/7/27
 */
class DishAdapter : ListAdapter<DishItem, DishViewHolder>(DishDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val binding = ItemDishNumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.tvName.text = getItem(position).name
        holder.tVNum.text = "x${getItem(position).quantity}"
    }
}

class DishViewHolder(binding: ItemDishNumBinding) : RecyclerView.ViewHolder(binding.root) {
    val tvName: TextView = binding.name
    val tVNum: TextView = binding.num
}

private class DishDiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<DishItem>() {
    override fun areItemsTheSame(oldItem: DishItem, newItem: DishItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DishItem, newItem: DishItem): Boolean {
        return oldItem == newItem
    }
}