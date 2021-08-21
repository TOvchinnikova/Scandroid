package com.t_ovchinnikova.android.scandroid_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.t_ovchinnikova.android.scandroid_2.databinding.ActivityMainBinding
import com.t_ovchinnikova.android.scandroid_2.ui.ScanningFragment
import com.t_ovchinnikova.android.scandroid_2.ui.ScanningHistoryFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null)
            setupBottomNavigation(binding.bottomNavigationView.selectedItemId)

        binding.bottomNavigationView.setOnItemSelectedListener  {
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