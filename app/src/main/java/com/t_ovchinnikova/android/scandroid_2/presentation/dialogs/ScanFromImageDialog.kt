package com.t_ovchinnikova.android.scandroid_2.presentation.dialogs

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanFromImageDialogBinding

class ScanFromImageDialog : BaseBottomSheetDialog() {

    private lateinit var binding: FragmentScanFromImageDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanFromImageDialogBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseImageGallery()
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        //intent.type = "image/*"
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

    companion object {
        fun newInstance(): ScanFromImageDialog {
            return ScanFromImageDialog()
        }
    }
}