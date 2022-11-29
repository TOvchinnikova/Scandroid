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
import com.t_ovchinnikova.android.scandroid_2.launchWhenStarted
import com.t_ovchinnikova.android.scandroid_2.presentation.HistoryFragment
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanningFragment
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanResultViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanningViewModel
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ScanResultDialog : BaseBottomSheetDialog(), EditCodeNoteListener, DeleteCodeListener {

    private lateinit var binding: FragmentScanResultDialogBinding

    private val viewModel: ScanResultViewModel by viewModel()

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

     //   if (savedInstanceState == null) {
//            resultCode = arguments?.getParcelable(ARG_SCAN_RESULT)
                //?: throw RuntimeException("Required arguments were not passed")
            //viewModel.editCode(resultCode)
            //editedCode = resultCode.copy()
//        } else {
//            editedCode = viewModel.code.value ?: throw RuntimeException("Unknown code")
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
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
                copyToClipboard(ivEditNote.text)
            }
            buttonSearchOnInternet.setOnClickListener {
                searchWeb(editedCode.text)
            }
            buttonSend.setOnClickListener {
                sendText(editedCode.text, editedCode.note)
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
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.delete -> showDeleteDialog()
                            R.id.isFavorite -> toggleIsFavorite()
                            R.id.close -> dismiss()
                            else -> throw RuntimeException("Unknown clicked item")
                        }
                        return@setOnMenuItemClickListener true
                    }
                }
                ivEditNote.setOnClickListener {
                    showEditDialog(code.note)
                }
            }
            showCodeIsFavorite(it.isFavorite)
            showNote(it.note)
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

    private fun showNote(note: String) {
        binding.tvNote.text = note
        binding.tvNote.visibility = if (note.isNotBlank()) View.VISIBLE else View.GONE
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

    private fun deleteBarcode(id: Long) {
        viewModel.deleteBarcode(id)
        dismiss()
    }

    private fun toggleIsFavorite(code: Code) {
        //val isFavorite = code.isFavorite.not()
        viewModel.updateBarcode(
            code.copy(
                isFavorite = code.isFavorite.not()
            )
        )
        //showCodeIsFavorite(isFavorite)
    }

    override fun onNoteConfirmed(note: String) {
        editedCode.note = note
        viewModel.updateBarcode(editedCode)
        showNote()
    }

    override fun onDeleteConfirmed() {
        deleteBarcode()
    }

    companion object {
        private const val ARG_SCAN_RESULT = "scan result"

        fun newInstance(scanResult: Code): ScanResultDialog {
            return ScanResultDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SCAN_RESULT, scanResult)
                }
            }
        }
    }
}
