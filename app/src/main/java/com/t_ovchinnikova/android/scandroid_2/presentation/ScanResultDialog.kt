package com.t_ovchinnikova.android.scandroid_2.presentation

import android.content.DialogInterface
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.databinding.DialogEditCodeNoteBinding
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanResultDialogBinding
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import com.t_ovchinnikova.android.scandroid_2.domain.typeToString
import java.text.SimpleDateFormat
import java.util.*

class ScanResultDialog: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentScanResultDialogBinding
    private lateinit var resultCode: Code
    private lateinit var editedCode: Code
    private val codeRepository = CodeRepository.get()

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
        initView()
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        parentFragment?.let {
            ViewModelProvider(it).get(ScanningViewModel::class.java).setScannerWorkState(true)
        }
    }

    private fun initView() {
        binding.tvType.text = getString(editedCode.typeToString())
        binding.tvResult.text = editedCode.text.toString()
        val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
        binding.tvDate.text = dateFormatter.format(editedCode.date)
        showCodeIsFavorite(editedCode.isFavorite)
        showNote()
        binding.toolbar.apply {
            setTitle(editedCode.formatToStringId())
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> showDeleteDialog()
                    R.id.isFavorite -> toggleIsFavorite()
                    else -> throw RuntimeException("Unknown clicked item")
                }
                return@setOnMenuItemClickListener true
            }
        }
        binding.ivEditNote.setOnClickListener {
            showEditDialog()
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
        binding.toolbar.menu.findItem(R.id.isFavorite).icon = ContextCompat.getDrawable(requireContext(), iconId)
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
                        updateBarcode(editedCode)
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
        codeRepository.deleteCode(editedCode.id)
        dismiss()
    }

    private fun toggleIsFavorite() {
        val isFavorite = editedCode.isFavorite.not()
        editedCode.isFavorite = isFavorite
        Log.d("MyLog", "toggleIsFavorite $editedCode")
        updateBarcode(editedCode)
        showCodeIsFavorite(isFavorite)
    }

    private fun updateBarcode(code: Code) {
        codeRepository.updateCode(code)
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