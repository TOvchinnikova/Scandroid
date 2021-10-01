package com.t_ovchinnikova.android.scandroid_2.presentation

import android.app.Dialog
import android.app.SearchManager
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanResultDialogBinding
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import com.t_ovchinnikova.android.scandroid_2.domain.typeToString
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NAME_SHADOWING")
class ScanResultDialog : BottomSheetDialogFragment(), EditCodeNoteListener, DeleteCodeListener {

    private lateinit var binding: FragmentScanResultDialogBinding
    private lateinit var resultCode: Code
    private lateinit var editedCode: Code

    private val viewModel by viewModels<ScanResultViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                setupFullWidth(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            parentFragment?.let {
                ViewModelProvider(it).get(ScanningViewModel::class.java).setScannerWorkState(false)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanResultDialogBinding.inflate(inflater, container, false)

        if (savedInstanceState == null) {
            resultCode = arguments?.getSerializable(ARG_SCAN_RESULT) as Code
            viewModel.editCode(resultCode)
            editedCode = resultCode.copy()
        } else {
            editedCode = viewModel.code.value ?: throw RuntimeException("Unknown code")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupClickListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (parentFragment is ScanningFragment) {
            parentFragment?.let {
                ViewModelProvider(it).get(ScanningViewModel::class.java).setScannerWorkState(true)
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
                copyToClipboard(resultCode.text)
            }
            buttonSearchOnInternet.setOnClickListener {
                searchWeb(resultCode.text)
            }
            buttonSend.setOnClickListener {
                sendText(resultCode.text)
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

    private fun initView() {
        val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
        with(binding) {
            tvType.text = getString(editedCode.typeToString())
            tvResult.text = editedCode.text
            tvDate.text = dateFormatter.format(editedCode.date)
            toolbar.apply {
                setTitle(editedCode.formatToStringId())
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
                showEditDialog()
            }
        }
        showCodeIsFavorite(editedCode.isFavorite)
        showNote()
    }

    private fun searchWeb(queryText: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, queryText)
        }
        startAction(intent)
    }

    private fun sendText(text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
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

    private fun showNote() {
        binding.tvNote.text = editedCode.note
        binding.tvNote.visibility = if (editedCode.note != "") View.VISIBLE else View.GONE
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
        val dialog = DeleteCodeDialogFragment.newInstance()
        dialog.show(childFragmentManager, "")
    }

    private fun showEditDialog() {
        val dialog = EditCodeNoteDialogFragment.newInstance(editedCode.note)
        dialog.show(childFragmentManager, "")
    }

    private fun deleteBarcode() {
        viewModel.deleteBarcode(editedCode.id)
        dismiss()
    }

    private fun toggleIsFavorite() {
        val isFavorite = editedCode.isFavorite.not()
        editedCode.isFavorite = isFavorite
        viewModel.updateBarcode(editedCode)
        showCodeIsFavorite(isFavorite)
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun setupFullWidth(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onNoteConfirmed(note: String) {
        editedCode.note = note
        viewModel.updateBarcode(editedCode)
        showNote()
    }

    override fun onNoteConfirmed() {
        deleteBarcode()
    }

    companion object {
        private const val ARG_SCAN_RESULT = "scan result"

        fun newInstance(scanResult: Code): ScanResultDialog {
            return ScanResultDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_SCAN_RESULT, scanResult)
                }
            }
        }
    }
}
