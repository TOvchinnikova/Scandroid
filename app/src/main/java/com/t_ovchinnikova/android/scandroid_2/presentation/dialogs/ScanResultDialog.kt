package com.t_ovchinnikova.android.scandroid_2.presentation.dialogs

import android.app.SearchManager
import android.content.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanResultDialogBinding
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import com.t_ovchinnikova.android.scandroid_2.domain.typeToString
import com.t_ovchinnikova.android.scandroid_2.utils.launchWhenStarted
import com.t_ovchinnikova.android.scandroid_2.presentation.fragments.HistoryFragment
import com.t_ovchinnikova.android.scandroid_2.presentation.fragments.ScanningFragment
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanResultViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanningViewModel
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class ScanResultDialog : BaseBottomSheetDialog(), EditCodeNoteListener, DeleteCodeListener {

    private lateinit var binding: FragmentScanResultDialogBinding


    private val codeId by lazy (LazyThreadSafetyMode.NONE) {
        arguments?.getLong(SCAN_CODE_ID) as Long
    }

    private val viewModel: ScanResultViewModel by viewModel {
        parametersOf(
            codeId
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            if (parentFragment is ScanningFragment) {
                parentFragment?.let {
                    ViewModelProvider(it).get(ScanningViewModel::class.java)
                        .setScannerState(ScanningViewModel.ScannerWorkState.ScanInactive)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentScanResultDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupClickListeners()
    }

    private fun observeViewModel() {
        viewModel.code.onEach {
            showContent(it)
        }
            .launchWhenStarted(lifecycleScope)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (parentFragment is ScanningFragment) {
            parentFragment?.let {
                ViewModelProvider(it).get(ScanningViewModel::class.java).setScannerState(ScanningViewModel.ScannerWorkState.ScannerActive)
            }
        }
        if (parentFragment is HistoryFragment) {
            parentFragment?.let {
                ViewModelProvider(it).get(HistoryViewModel::class.java).showCodeDialog(false)
            }
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            buttonCopy.setOnClickListener {
                copyToClipboard(tvResult.text.toString())
            }
            buttonSearchOnInternet.setOnClickListener {
                searchWeb(tvResult.text.toString())
            }
            buttonSend.setOnClickListener {
                sendText(tvResult.text.toString(), tvNote.text.toString())
            }
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("code text", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), R.string.barcode_copied, Toast.LENGTH_SHORT).show()
    }

    private fun showContent(code: Code?) {
        code?.let {
            val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
            with(binding) {
                tvType.text = getString(it.typeToString())
                tvResult.text = it.text
                tvDate.text = dateFormatter.format(it.date)
                toolbar.apply {
                    setTitle(it.formatToStringId())
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.delete -> showDeleteDialog()
                            R.id.isFavorite -> toggleIsFavorite()
                            R.id.close -> dismiss()
                            else -> throw RuntimeException("Unknown clicked item")
                        }
                        return@setOnMenuItemClickListener true
                    }
                }
                binding.tvNote.text = it.note
                binding.tvNote.visibility = if (it.note.isNotBlank()) View.VISIBLE else View.GONE
                ivEditNote.setOnClickListener {
                    showEditDialog(code.note)
                }
            }
            showCodeIsFavorite(it.isFavorite)
        }
    }

    private fun searchWeb(queryText: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, queryText)
        }
        startAction(intent)
    }

    private fun sendText(text: String, note: String) {
        val message =
            if (note.isNotBlank() && viewModel.getSettings()?.isSendingNoteWithCode == true)
                text + '\n' + note
            else
                text
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
        }
        startAction(intent)
    }

    private fun startAction(intent: Intent) {
        intent.apply {
            flags = flags or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            requireContext().startActivity(intent)
        }
    }

    private fun showCodeIsFavorite(isFavorite: Boolean) {
        val iconId = if (isFavorite)
            R.drawable.ic_favorite_on
        else
            R.drawable.ic_favorite_off
        binding.toolbar.menu.findItem(R.id.isFavorite).icon =
            ContextCompat.getDrawable(requireContext(), iconId)
    }

    private fun showDeleteDialog() {
        val dialog = DeleteCodeDialogFragment.newInstance(DeleteCodeDialogFragment.DELETE_CODE)
        dialog.show(childFragmentManager, "")
    }

    private fun showEditDialog(note: String) {
        val dialog = EditCodeNoteDialogFragment.newInstance(note)
        dialog.show(childFragmentManager, "")
    }

    private fun toggleIsFavorite() {
        viewModel.code.value?.let {
            viewModel.updateBarcode(
                it.copy(
                    isFavorite = it.isFavorite.not()
                )
            )
        }
    }

    override fun onNoteConfirmed(note: String) {
        viewModel.code.value?.let {
            viewModel.updateBarcode(
                it.copy(
                    note = note
                )
            )
        }
    }

    override fun onDeleteConfirmed() {
        viewModel.deleteBarcode(codeId)
        dismiss()
    }

    companion object {
        private const val SCAN_CODE_ID = "SCAN CODE ID"

        fun newInstance(codeId: Long): ScanResultDialog {
            return ScanResultDialog().apply {
                arguments = Bundle().apply {
                    putLong(SCAN_CODE_ID, codeId)
                }
            }
        }
    }
}
