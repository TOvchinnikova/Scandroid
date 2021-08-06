package com.t_ovchinnikova.android.scandroid_2.ui

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

const val ARG_SCAN_RESULT = "scan result"

class ScanResultDialog(private val listener: DialogDismissListener) : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(scanResult: String, listener:DialogDismissListener): ScanResultDialog {

            val args = Bundle().apply {
                putString(ARG_SCAN_RESULT, scanResult)
            }
            val fragment = ScanResultDialog(listener)
            fragment.arguments = args
            return fragment

        }
    }

    interface DialogDismissListener {
        fun onDismiss()
    }

}