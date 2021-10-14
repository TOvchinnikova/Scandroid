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
import android.app.Activity
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.t_ovchinnikova.android.scandroid_2.R


class HistoryFragment : Fragment(), DeleteCodeListener {

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
        setupView()
        setupRecyclerView()
        setupSwipeListener(rvHistoryList)
        setupViewModel()
    }

    private fun setupView() {
        with(binding) {
            with(searchContainer) {
                ivClear.setOnClickListener {
                    etSearch.setText("")
                    hideKeyboard(requireActivity())
                    view?.clearFocus()
                }
                etSearch.setOnFocusChangeListener { _, isFocused ->
                    if (isFocused) {
                        ivClear.visibility = View.VISIBLE
                    } else {
                        ivClear.visibility = View.GONE
                    }
                }
                etSearch.doOnTextChanged { text, _, _, _ ->
                    filterList(text.toString())
                }
                etSearch.setOnKeyListener { view, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        hideKeyboard(requireActivity())
                        view?.clearFocus()
                    }
                    return@setOnKeyListener true
                }
            }
            tbHistory.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> showDeleteDialog()
                    else -> throw RuntimeException("Unknown clicked item")
                }
                return@setOnMenuItemClickListener true
            }
        }
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

    private fun setupViewModel() {
        viewModel.codeListLiveData.observe(viewLifecycleOwner, {
            val list =
                it.filter { it.text.contains(binding.searchContainer.etSearch.text.toString()) }
            codeListAdapter.submitList(list)
        })
    }

    private fun filterList(filterText: String) {
        val list = viewModel.codeListLiveData.value?.filter { it.text.contains(filterText) }
        codeListAdapter.submitList(list)
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    private fun showDeleteDialog() {
        val dialog = DeleteCodeDialogFragment.newInstance(DeleteCodeDialogFragment.DELETE_ALL_CODES)
        dialog.show(childFragmentManager, "")
    }

    override fun onDeleteConfirmed() {
        viewModel.deleteAllCodes()
    }

}