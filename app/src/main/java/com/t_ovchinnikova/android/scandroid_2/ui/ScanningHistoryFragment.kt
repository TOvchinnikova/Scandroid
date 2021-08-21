package com.t_ovchinnikova.android.scandroid_2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanningHistoryBinding

class ScanningHistoryFragment : Fragment() {

    private lateinit var rvHistory: RecyclerView
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

        viewModel.codeListLiveData.observe(viewLifecycleOwner, Observer {
            codeListAdapter.submitList(it)
        })

    }

    private fun setupRecyclerView() {
        rvHistory = binding.rvHistoryList
        codeListAdapter = CodeHistoryListAdapter()
        rvHistory.adapter = codeListAdapter
        rvHistory.recycledViewPool.setMaxRecycledViews(
            CodeHistoryListAdapter.VIEW_TYPE_DEFAULT,
            CodeHistoryListAdapter.MAX_POOL_SIZE
        )


    }

    companion object {

        fun newInstance(): ScanningHistoryFragment {
            return ScanningHistoryFragment()
        }
    }

}