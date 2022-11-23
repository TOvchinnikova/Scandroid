package com.t_ovchinnikova.android.scandroid_2.presentation.dialogs

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.PickImage
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanFromImageDialogBinding
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanAnalyzer
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener
import java.io.IOException


class ScanFromImageDialog : BaseBottomSheetDialog() {

    private lateinit var binding: FragmentScanFromImageDialogBinding

    private val pickImageFromGallery = registerForActivityResult(PickImage()) {
            uri: Uri? ->
        uri?.let {
            binding.scanImage.setImageUriAsync(uri)
            val image: InputImage
            try {
                image = InputImage.fromFilePath(requireContext(), uri)
                val scanListener = ScanListener()
                val analyzer = ScanAnalyzer(scanListener)
                analyzer.recognizeCode(image)
            } catch (e: IOException) {
                e.printStackTrace()
            }
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

    inner class ScanListener : ScanResultListener {

        override fun onScanned(resultCode: Code) {
            Log.d("MyLog", "onScanned resultCode: $resultCode")
            binding.scanIntermediateResult.text = getString(R.string.scan_result_from_image, getString(resultCode.formatToStringId()), resultCode.text)
        }
    }

    companion object {
        fun newInstance(): ScanFromImageDialog {
            return ScanFromImageDialog()
        }
    }
}