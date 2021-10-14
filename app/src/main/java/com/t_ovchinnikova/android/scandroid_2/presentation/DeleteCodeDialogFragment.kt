package com.t_ovchinnikova.android.scandroid_2.presentation

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.t_ovchinnikova.android.scandroid_2.R

class DeleteCodeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val deleteType = arguments?.getString(DELETE_TYPE)
            ?: throw RuntimeException("Required arguments were not passed")
        val listener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val listener = requireParentFragment() as DeleteCodeListener
                    listener.onDeleteConfirmed()
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
            }
        }
        val title =
            if (deleteType == DELETE_ALL_CODES)
                R.string.delete_all_question_dialog
            else
                R.string.delete_question_dialog

        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setCancelable(false)
            .setIcon(R.drawable.ic_delete_dialog)
            .setTitle(title)
            .setPositiveButton(R.string.yes, listener)
            .setNegativeButton(R.string.no, listener)
            .create()
    }

    companion object {
        const val DELETE_CODE = "delete_code"
        const val DELETE_ALL_CODES = "delete_all_codes"
        const val DELETE_TYPE = "delete_type"

        fun newInstance(deleteType: String): DeleteCodeDialogFragment {
            return DeleteCodeDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(DELETE_TYPE, deleteType)
                }
            }
        }
    }
}