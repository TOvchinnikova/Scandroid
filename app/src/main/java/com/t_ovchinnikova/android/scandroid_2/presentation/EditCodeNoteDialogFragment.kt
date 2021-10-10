package com.t_ovchinnikova.android.scandroid_2.presentation

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.databinding.DialogEditCodeNoteBinding

class EditCodeNoteDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val note = arguments?.getString(CODE_NOTE_KEY) ?: ""
        val noteBinding = DialogEditCodeNoteBinding.inflate(layoutInflater)
        noteBinding.etNote.setText(note)
        val listener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val listener = requireParentFragment() as EditCodeNoteListener
                    val newNote = noteBinding.etNote.text.toString()
                    listener.onNoteConfirmed(newNote)
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
            }
        }

        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
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