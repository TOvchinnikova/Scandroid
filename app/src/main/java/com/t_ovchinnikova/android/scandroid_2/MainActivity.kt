package com.t_ovchinnikova.android.scandroid_2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.t_ovchinnikova.android.scandroid_2.databinding.ActivityMainBinding
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanningFragment
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanningHistoryFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNavigationView

        if(savedInstanceState == null)
            setupBottomNavigation(R.id.menu_scanner)

        bottomNavigationView.setOnItemSelectedListener  {
            if (it.itemId != bottomNavigationView.selectedItemId)
                setupBottomNavigation(it.itemId)

            true
        }
    }

    private fun setupBottomNavigation(bottomItemId: Int) {
        when (bottomItemId) {
            R.id.menu_scanner -> replaceFragment(ScanningFragment.newInstance())
            R.id.menu_history -> replaceFragment(ScanningHistoryFragment.newInstance())
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