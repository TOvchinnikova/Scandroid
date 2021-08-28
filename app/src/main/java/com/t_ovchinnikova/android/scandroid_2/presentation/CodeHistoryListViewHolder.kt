package com.t_ovchinnikova.android.scandroid_2.presentation

import androidx.recyclerview.widget.RecyclerView
import com.t_ovchinnikova.android.scandroid_2.databinding.ItemCodeBinding

class CodeHistoryListViewHolder(binding: ItemCodeBinding) : RecyclerView.ViewHolder(binding.root) {

    val tvCode = binding.tvCode
    val tvDate = binding.tvDate
    val tvFormat = binding.tvFormat
    val ivCode = binding.ivCode

}