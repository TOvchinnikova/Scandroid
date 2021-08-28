package com.t_ovchinnikova.android.scandroid_2.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanResultDialogBinding
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import com.t_ovchinnikova.android.scandroid_2.domain.typeToString
import java.text.SimpleDateFormat
import java.util.*

class ScanResultDialog: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentScanResultDialogBinding
    private lateinit var resultCode: Code
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
        initView()
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        parentFragment?.let {
            ViewModelProvider(it).get(ScanningViewModel::class.java).setScannerWorkState(true)
        }
    }

    fun initView() {
        binding.tvType.text = getString(resultCode.typeToString())
        binding.tvResult.text = resultCode.text.toString()
        val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
        binding.tvDate.text = dateFormatter.format(resultCode.date)
        showCodeIsFavorite(resultCode.isFavorite)
        binding.toolbar.apply {
            setTitle(resultCode.formatToStringId())
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> showDeleteDialog()
                    else -> toggleIsFavorite()
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    fun showCodeIsFavorite(isFavorite: Boolean) {
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

    private fun deleteBarcode() {
        Log.d("MyLog", "deleteCode")
        codeRepository.deleteCode(resultCode.id)
        dismiss()
    }

    fun toggleIsFavorite() {
        val isFavorite = resultCode.isFavorite.not()
        showCodeIsFavorite(isFavorite)
        resultCode.isFavorite = isFavorite
        codeRepository.updateCode(resultCode)
    }

    companion object {
        private const val ARG_SCAN_RESULT = "scan result"

        fun newInstance(scanResult: Code): ScanResultDialog {

            val args = Bundle().apply {
                putSerializable(ARG_SCAN_RESULT, scanResult)
            }

            val fragment = ScanResultDialog()
            fragment.arguments = args

            return fragment
        }
    }
}