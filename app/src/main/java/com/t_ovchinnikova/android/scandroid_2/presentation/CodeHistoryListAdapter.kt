package com.t_ovchinnikova.android.scandroid_2.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.t_ovchinnikova.android.scandroid_2.databinding.ItemCodeBinding
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToImageId
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import java.text.SimpleDateFormat
import java.util.*

class CodeHistoryListAdapter : ListAdapter<Code, CodeHistoryListViewHolder>(CodeItemDiffCallback()) {

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)

    var onCodeItemClickListener: ((Code) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeHistoryListViewHolder {
        val binding = ItemCodeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return CodeHistoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CodeHistoryListViewHolder, position: Int) {
        val code = getItem(position)
        Log.d("MyLog", "$code")
        holder.tvCode.text = code.text
        holder.tvDate.text = dateFormatter.format(code.date)
        holder.ivIsFavorite.isActivated = code.isFavorite
        holder.tvFormat.setText(code.formatToStringId())
        holder.ivCode.setImageResource(code.formatToImageId())
        holder.itemView.setOnClickListener {
            onCodeItemClickListener?.invoke(code)
        }
    }

    companion object {
        const val VIEW_TYPE_DEFAULT = 0
        const val MAX_POOL_SIZE = 15
    }
}