package com.pax.kdsdemo.kitchen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pax.kdsdemo.kitchen.MainViewModel
import com.pax.kdsdemo.kitchen.data.TableDish
import com.pax.kdsdemo.kitchen.databinding.ItemTestShowBinding

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
        holder.textView.text = item.tableName
        val innerAdapterDish = DishAdapter()
        holder.rw.adapter = innerAdapterDish
        innerAdapterDish.submitList(item.dishes)
        holder.complete.setOnClickListener {
            deleteItem(item)
        }
    }

    private fun deleteItem(item: TableDish) {
        viewModel.deleteTableDishItem(item)
    }
}

class TestShowViewHolder(binding: ItemTestShowBinding) : RecyclerView.ViewHolder(binding.root) {
    val textView: TextView = binding.textViewItemShow
    val rw: RecyclerView = binding.rvDish
    val complete: MaterialButton = binding.btnComplete
}

private class TestShowDiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<TableDish>() {
    override fun areItemsTheSame(oldItem: TableDish, newItem: TableDish): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TableDish, newItem: TableDish): Boolean {
        return oldItem == newItem
    }
}