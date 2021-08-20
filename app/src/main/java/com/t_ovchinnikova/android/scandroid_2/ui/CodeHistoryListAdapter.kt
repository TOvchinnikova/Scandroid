package com.t_ovchinnikova.android.scandroid_2.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.t_ovchinnikova.android.scandroid_2.databinding.ItemCodeBinding
import com.t_ovchinnikova.android.scandroid_2.model.Code

class CodeHistoryListAdapter : ListAdapter<Code, CodeHistoryListViewHolder>(CodeItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeHistoryListViewHolder {
        Log.d("MyLog", "onCreateViewHolder")
        val binding = ItemCodeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        //LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return CodeHistoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CodeHistoryListViewHolder, position: Int) {
        Log.d("MyLog", "onBindViewHolder")
        val code = getItem(position)

        holder.tvCode.text = code.text
        holder.tvDate.text = code.date.toString()
    }


    companion object {

        const val VIEW_TYPE_DEFAULT = 0

        const val MAX_POOL_SIZE = 15

    }
}