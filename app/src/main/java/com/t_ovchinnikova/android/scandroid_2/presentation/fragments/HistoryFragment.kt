package com.t_ovchinnikova.android.scandroid_2.presentation.fragments

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanningHistoryBinding
import com.t_ovchinnikova.android.scandroid_2.launchWhenStarted
import com.t_ovchinnikova.android.scandroid_2.presentation.adapters.CodeHistoryListAdapter
import com.t_ovchinnikova.android.scandroid_2.presentation.dialogs.DeleteCodeDialogFragment
import com.t_ovchinnikova.android.scandroid_2.presentation.dialogs.DeleteCodeListener
import com.t_ovchinnikova.android.scandroid_2.presentation.dialogs.ScanResultDialog
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.HistoryViewModel
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(), DeleteCodeListener {

    private lateinit var binding: FragmentScanningHistoryBinding

    private val codeListAdapter by lazy (LazyThreadSafetyMode.NONE) {
        CodeHistoryListAdapter { code ->
            if (viewModel.codeDialogShowed.value != true) {
                viewModel.showCodeDialog(true)
                ScanResultDialog.newInstance(code.id)
                    .show(childFragmentManager, ScanResultDialog::class.java.simpleName)
            }
        }
    }

    private val viewModel: HistoryViewModel by viewModel()

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
        setupSwipeListener()
        observeViewModel()
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
        binding.rvHistoryList.adapter = codeListAdapter
        binding.rvHistoryList.recycledViewPool.setMaxRecycledViews(
            CodeHistoryListAdapter.VIEW_TYPE_DEFAULT,
            CodeHistoryListAdapter.MAX_POOL_SIZE
        )
    }

    private fun observeViewModel() {
        viewModel.getCodeListObservable()
            .onEach {
                val list = it.filter { code ->
                    code.text.contains(binding.searchContainer.etSearch.text.toString())
                }
                codeListAdapter.submitList(list)
            }
            .launchWhenStarted(lifecycleScope)

        viewModel.getCodesHistoryStateObservable()
            .onEach { state ->
                when (state) {
                    is HistoryViewModel.CodesHistoryState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.historyIsEmptyHint.visibility = View.GONE
                        binding.rvHistoryList.visibility = View.GONE
                    }
                    is HistoryViewModel.CodesHistoryState.ReadyToShow -> {
                        binding.progressBar.visibility = View.GONE
                        setHistoryListVisibility(state.codes.isEmpty())
                    }
                }
            }
            .launchWhenStarted(lifecycleScope)
    }

    private fun setupSwipeListener() {
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

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    canvas,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val itemView = viewHolder.itemView
                val itemHeight = itemView.height
                val background = ColorDrawable()
                when {
                    dX < 0 -> {
                        background.color = Color.RED
                        background.setBounds(
                            itemView.right + dX.toInt(),
                            itemView.top,
                            itemView.right,
                            itemView.bottom
                        )
                        background.draw(canvas)

                        val deleteIcon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
                        val intrinsicWidth = deleteIcon?.intrinsicWidth
                            ?: throw RuntimeException("Unknown intrinsicWidth")
                        val intrinsicHeight = deleteIcon.intrinsicHeight
                        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
                        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
                        val deleteIconRight = itemView.right - deleteIconMargin
                        val deleteIconBottom = deleteIconTop + intrinsicHeight

                        deleteIcon.setBounds(
                            deleteIconLeft,
                            deleteIconTop,
                            deleteIconRight,
                            deleteIconBottom
                        )
                        deleteIcon.draw(canvas)
                    }
                    else -> {
                        background.setBounds(0, 0, 0, 0)
                        background.draw(canvas)
                    }
                }
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.rvHistoryList)
    }

    private fun filterList(filterText: String) {
        val list = viewModel.getCodeListObservable().value.filter { it.text.contains(filterText) }
        setHistoryListVisibility(list.isEmpty())
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

    private fun showDeleteDialog() {
        val dialog = DeleteCodeDialogFragment.newInstance(DeleteCodeDialogFragment.DELETE_ALL_CODES)
        dialog.show(childFragmentManager, "")
    }

    private fun setHistoryListVisibility(isEmpty: Boolean) {
        binding.historyIsEmptyHint.visibility =
            if (isEmpty) View.VISIBLE else View.GONE
        binding.rvHistoryList.visibility =
            if (isEmpty) View.GONE else View.VISIBLE
    }

    override fun onDeleteConfirmed() {
        viewModel.deleteAllCodes()
    }

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

}