package com.t_ovchinnikova.android.scandroid_2.presentation

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.t_ovchinnikova.android.scandroid_2.R

class DeleteCodeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val listener = requireParentFragment() as DeleteCodeListener
                    listener.onNoteConfirmed()
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
            }
        }
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setCancelable(false)
            .setIcon(R.drawable.ic_delete_dialog)
            .setTitle(R.string.delete_question_dialog)
            .setPositiveButton(R.string.yes, listener)
            .setNegativeButton(R.string.no, listener)
            .create()
    }

    companion object {
        fun newInstance(): DeleteCodeDialogFragment {
            return DeleteCodeDialogFragment()
        }
    }
}