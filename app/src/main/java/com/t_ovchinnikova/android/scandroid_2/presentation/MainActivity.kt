package com.t_ovchinnikova.android.scandroid_2.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = binding.bottomNavigationView
        if (savedInstanceState == null)
            setupBottomNavigation(R.id.menu_scanner)
        bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId != bottomNavigationView.selectedItemId)
                setupBottomNavigation(it.itemId)
            true
        }
    }

    private fun setupBottomNavigation(bottomItemId: Int) {
        when (bottomItemId) {
            R.id.menu_scanner -> replaceFragment(ScanningFragment.newInstance())
            R.id.menu_history -> replaceFragment(HistoryFragment.newInstance())
            R.id.menu_settings -> replaceFragment(SettingsFragment.newInstance())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setReorderingAllowed(true)
            .commit()
    }
}