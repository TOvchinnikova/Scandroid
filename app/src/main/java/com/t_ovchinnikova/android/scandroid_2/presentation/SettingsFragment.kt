package com.t_ovchinnikova.android.scandroid_2.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.t_ovchinnikova.android.scandroid_2.SettingsData
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentSettingsBinding
import com.t_ovchinnikova.android.scandroid_2.launchWhenStarted
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun setupView() {
        with(binding) {
            swFlash.setOnCheckedChangeListener { _, isChecked ->
                viewModel.saveSettings(getNewSettingsData().copy(isFlashlightWhenAppStarts = isChecked))
            }
            swSave.setOnCheckedChangeListener { _, isChecked ->
                viewModel.saveSettings(getNewSettingsData().copy(isSaveScannedBarcodesToHistory = isChecked))
            }
            swVibrate.setOnCheckedChangeListener { _, isChecked ->
                viewModel.saveSettings(getNewSettingsData().copy(isVibrationOnScan = isChecked))
            }
            swSendNote.setOnCheckedChangeListener { _, isChecked ->
                viewModel.saveSettings(getNewSettingsData().copy(isSendingNoteWithCode = isChecked))
            }
        }
    }

    private fun observeViewModel() {
        viewModel.getSettingsObservable()
            .onEach {
                it?.let {
                    showSettings(it)
                }
            }.launchWhenStarted(lifecycleScope)
        viewModel.getLoadingStateObservable()
            .onEach {
                when (it) {
                    is SettingsViewModel.SettingsLoadingState.Show -> {
                        binding.settingsGroup.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is SettingsViewModel.SettingsLoadingState.Hide -> {
                        binding.settingsGroup.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }.launchWhenStarted(lifecycleScope)
    }

    private fun showSettings(settings: SettingsData) {
        with(binding) {
            swFlash.isChecked = settings.isFlashlightWhenAppStarts
            swVibrate.isChecked = settings.isVibrationOnScan
            swSave.isChecked = settings.isSaveScannedBarcodesToHistory
            swSendNote.isChecked = settings.isSendingNoteWithCode
        }
    }

    private fun getNewSettingsData(): SettingsData {
        return SettingsData(
            isVibrationOnScan = binding.swVibrate.isChecked,
            isSendingNoteWithCode = binding.swSendNote.isChecked,
            isFlashlightWhenAppStarts = binding.swFlash.isChecked,
            isSaveScannedBarcodesToHistory = binding.swSave.isChecked
        )
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

}