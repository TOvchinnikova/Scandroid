package com.t_ovchinnikova.android.scandroid_2.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.t_ovchinnikova.android.scandroid_2.Settings
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var settings: Settings
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = Settings(requireContext())
    }

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
    }

    private fun setupView() {
        with(binding){
            swFlash.isChecked = settings.flash
            swVibrate.isChecked = settings.vibrate
            swSave.isChecked = settings.saveScannedBarcodesToHistory
            swFlash.setOnCheckedChangeListener { _, _ ->
                settings.flash = swFlash.isChecked
            }
            swSave.setOnCheckedChangeListener { _, _ ->
                settings.saveScannedBarcodesToHistory = swSave.isChecked
            }
            swVibrate.setOnCheckedChangeListener { _, _ ->
                settings.vibrate = swVibrate.isChecked
            }
        }
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

}