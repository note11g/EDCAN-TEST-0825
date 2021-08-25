package com.note11.baserecyclerexample.ui.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRcv {
    abstract class Adapter<I : Any, B : ViewDataBinding>(
        itemSame: (I, I) -> Boolean,
        private val itemLayoutId: Int,
        private val itemBindingID: Int,
        private val onItemClick: ((I) -> Unit)? = null
    ) : ListAdapter<I, ViewHolder<I, B>>(DiffCallBack<I>(itemSame)) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<I, B> {
            val binding: B = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                itemLayoutId,
                parent,
                false
            )

            val viewHolder = object : ViewHolder<I, B>(binding, itemBindingID) {}

            onItemClick?.let { f ->
                binding.root.setOnClickListener {
                    f(getItem(viewHolder.absoluteAdapterPosition))
                }
            }

            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder<I, B>, position: Int) {
            holder.bind(getItem(position))
        }
    }

    abstract class ViewHolder<I : Any, B : ViewDataBinding>(
        private val binding: B,
        private val bindingVarId: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: I) {
            binding.setVariable(bindingVarId, item)
            binding.executePendingBindings()
        }
    }

    private class DiffCallBack<I : Any>(private val itemSame: (I, I) -> Boolean) :
        DiffUtil.ItemCallback<I>() {
        override fun areItemsTheSame(oldItem: I, newItem: I): Boolean =
            itemSame(oldItem, newItem)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: I, newItem: I): Boolean =
            oldItem == newItem
    }
}