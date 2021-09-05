package com.t_ovchinnikova.android.scandroid_2.presentation

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.barhopper.Barcode.WIFI
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.mlkit.vision.barcode.Barcode
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.databinding.DialogEditCodeNoteBinding
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanResultDialogBinding
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import com.t_ovchinnikova.android.scandroid_2.domain.typeToString
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NAME_SHADOWING")
class ScanResultDialog: BottomSheetDialogFragment() {

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
        resultCode = arguments?.getSerializable(ARG_SCAN_RESULT) as Code
        editedCode = resultCode.copy()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupClickListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        parentFragment?.let {
            ViewModelProvider(it).get(ScanningViewModel::class.java).setScannerWorkState(true)
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            buttonCopy.setOnClickListener {
                viewModel.copyToClipboard(resultCode.text)
                Toast.makeText(requireContext(), R.string.barcode_copied, Toast.LENGTH_SHORT).show()
            }
            buttonSearchOnInternet.setOnClickListener {
                searchWeb()
            }
            buttonSend.setOnClickListener {
                sendText()
            }
        }
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

    private fun searchWeb() {
        viewModel.searchWeb(editedCode.text)
    }

    private fun sendText() {
        viewModel.sendText(editedCode.text)
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
        val listener = DialogInterface.OnClickListener { dialog, which ->
            when(which) {
                DialogInterface.BUTTON_POSITIVE -> deleteBarcode()
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setIcon(R.drawable.ic_delete_light_blue)
            .setTitle(R.string.delete_question_dialog)
            .setPositiveButton(R.string.yes, listener)
            .setNegativeButton(R.string.no, listener)
            .create()
        dialog.show()
    }

    private fun showEditDialog() {
        val noteBinding = DialogEditCodeNoteBinding.inflate(layoutInflater)
        noteBinding.etNote.setText(editedCode.note)
        val listener = DialogInterface.OnClickListener{ dialog, which ->
            when(which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val note = noteBinding.etNote.text.toString()
                    if (editedCode.note != note) {
                        editedCode.note = note
                        viewModel.updateBarcode(editedCode)
                        showNote()
                    }
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setIcon(R.drawable.ic_edit_light_blue)
            .setTitle(R.string.note)
            .setView(noteBinding.root)
            .setPositiveButton(R.string.save, listener)
            .setNegativeButton(R.string.cancel, listener)
            .create()
        dialog.show()
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