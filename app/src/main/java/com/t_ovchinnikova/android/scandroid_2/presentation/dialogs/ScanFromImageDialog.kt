package com.t_ovchinnikova.android.scandroid_2.presentation.dialogs

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.t_ovchinnikova.android.scandroid_2.PickImage
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanFromImageDialogBinding


class ScanFromImageDialog : BaseBottomSheetDialog() {

    private lateinit var binding: FragmentScanFromImageDialogBinding

    private val pickImageFromGallery = registerForActivityResult(PickImage()) {
            uri: Uri? ->
        uri?.let {
            binding.scanImage.setImageUriAsync(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanFromImageDialogBinding.inflate(inflater, container, false)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.chooseImage -> chooseImageGallery()
                else -> throw RuntimeException("Unknown clicked item")
            }
            return@setOnMenuItemClickListener true
        }
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseImageGallery()
    }

    private fun chooseImageGallery() {
        pickImageFromGallery.launch("image/*")
    }

    companion object {
        fun newInstance(): ScanFromImageDialog {
            return ScanFromImageDialog()
        }
    }
}