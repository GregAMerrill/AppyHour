package com.example.appyhour.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appyhour.database.Bottle
import com.example.appyhour.databinding.ListItemBottleBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BarAdapter(val clickListener: BottleListener) : ListAdapter<DataItem,
        RecyclerView.ViewHolder>(BottleDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val barItem = getItem(position) as DataItem.BarItem
                holder.bind(clickListener, barItem.bottle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    fun submitBottleList(list: List<Bottle>?) {
        val items = when(list) {
            null -> mutableListOf<DataItem>()
            else -> list.map { DataItem.BarItem(it) }
        }
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemBottleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: BottleListener, item: Bottle) {
            binding.bottle = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBottleBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}


class BottleListener(val clickListener: (bottleId: Long) -> Unit) {
    fun onClick(bottle: Bottle) = clickListener(bottle.bottleId)
}

class BottleDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}


sealed class DataItem {
    data class BarItem(val bottle: Bottle): DataItem() {
        override val id = bottle.bottleId
    }
    abstract val id: Long
}