package com.t_ovchinnikova.android.scandroid_2.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanningHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var rvHistoryList: RecyclerView
    private lateinit var binding: FragmentScanningHistoryBinding
    private lateinit var codeListAdapter: CodeHistoryListAdapter

    private val viewModel by viewModels<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanningHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeListener(rvHistoryList)
        viewModel.codeListLiveData.observe(viewLifecycleOwner, {
            codeListAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        rvHistoryList = binding.rvHistoryList
        codeListAdapter = CodeHistoryListAdapter()
        rvHistoryList.adapter = codeListAdapter
        rvHistoryList.recycledViewPool.setMaxRecycledViews(
            CodeHistoryListAdapter.VIEW_TYPE_DEFAULT,
            CodeHistoryListAdapter.MAX_POOL_SIZE
        )
        setupClickListener()
    }

    private fun setupClickListener() {
        codeListAdapter.onCodeItemClickListener = {
            if (viewModel.codeDialogShowed.value != true) {
                viewModel.showCodeDialog(true)
                ScanResultDialog.newInstance(it)
                    .show(childFragmentManager, ScanResultDialog::class.java.simpleName)
            }
        }
    }

    private fun setupSwipeListener(rvHistory: RecyclerView?) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = codeListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteCode(item.id)
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(rvHistory)
    }

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

}