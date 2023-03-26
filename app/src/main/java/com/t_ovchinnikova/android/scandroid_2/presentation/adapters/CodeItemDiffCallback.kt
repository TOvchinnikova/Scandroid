package com.t_ovchinnikova.android.scandroid_2.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code

class CodeItemDiffCallback: DiffUtil.ItemCallback<Code>() {

    override fun areItemsTheSame(oldItem: Code, newItem: Code): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Code, newItem: Code): Boolean {
        return oldItem == newItem
    }
}