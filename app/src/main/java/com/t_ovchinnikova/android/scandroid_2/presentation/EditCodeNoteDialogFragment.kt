package com.t_ovchinnikova.android.scandroid_2.presentation

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.databinding.DialogEditCodeNoteBinding
import com.t_ovchinnikova.android.scandroid_2.presentation.view.EditCodeNoteListener

class EditCodeNoteDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val note = arguments?.getString(CODE_NOTE_KEY) ?: ""
        val noteBinding = DialogEditCodeNoteBinding.inflate(layoutInflater)
        noteBinding.etNote.setText(note)
        val listener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val listener = requireParentFragment() as EditCodeNoteListener
                    val note = noteBinding.etNote.text.toString()
                    listener.onNoteConfirmed(note)
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
            }
        }

        return AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setIcon(R.drawable.ic_edit_light_blue)
            .setTitle(R.string.note)
            .setView(noteBinding.root)
            .setPositiveButton(R.string.save, listener)
            .setNegativeButton(R.string.cancel, listener)
            .create()
    }

    companion object {

        private const val CODE_NOTE_KEY = "note_key"

        fun newInstance(note: String): EditCodeNoteDialogFragment {
            return EditCodeNoteDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(CODE_NOTE_KEY, note)
                }
            }
        }
    }
}