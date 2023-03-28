package com.t_ovchinnikova.android.scandroid_2.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.data.toImageId
import com.t_ovchinnikova.android.scandroid_2.data.toStringRes
import com.t_ovchinnikova.android.scandroid_2.databinding.ItemCodeBinding
import java.text.SimpleDateFormat
import java.util.*

class CodeHistoryListAdapter(
    private val onCodeItemClickListener: ((Code) -> Unit),
    private val onIsFavouriteClickListener: ((Code) -> Unit)
) : ListAdapter<Code, CodeHistoryListViewHolder>(CodeItemDiffCallback()) {

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeHistoryListViewHolder {
        val binding = ItemCodeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return CodeHistoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CodeHistoryListViewHolder, position: Int) {
        val code = getItem(position)
        with(holder) {
            tvCode.text = code.text
            tvDate.text = dateFormatter.format(code.date)
            tvNote.text = code.note
            ivIsFavorite.isActivated = code.isFavorite
            tvFormat.setText(code.format.toStringRes())
            ivCode.setImageResource(code.format.toImageId())
            itemView.setOnClickListener {
                onCodeItemClickListener(code)
            }
            ivIsFavorite.setOnClickListener {
                onIsFavouriteClickListener(code)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_DEFAULT = 0
        const val MAX_POOL_SIZE = 15
    }
}