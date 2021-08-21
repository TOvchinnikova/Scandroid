package com.t_ovchinnikova.android.scandroid_2.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.mlkit.vision.barcode.Barcode
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanResultDialogBinding
import com.t_ovchinnikova.android.scandroid_2.model.Code

class ScanResultDialog: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentScanResultDialogBinding

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
    ): View? {

        binding = FragmentScanResultDialogBinding.inflate(inflater, container, false)

        val resultCode: Code = arguments?.getSerializable(ARG_SCAN_RESULT) as Code


        binding.tvResult.text = resultCode.text
        binding.tvType.text = resultCode.type.toString()

        return binding.root

    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        parentFragment?.let {
            ViewModelProvider(it).get(ScanningViewModel::class.java).setScannerWorkState(true)
        }
    }

    companion object {

        private const val TAG = "ScanResultDialog"
        private const val ARG_SCAN_RESULT = "scan result"

        fun newInstance(scanResult: Code): ScanResultDialog {
//, fragmentManager: FragmentManager
            val args = Bundle().apply {
                putSerializable(ARG_SCAN_RESULT, scanResult)
            }

            val fragment = ScanResultDialog()
            fragment.arguments = args

            return fragment
            //fragment.show(fragmentManager, TAG)
        }

    }

}