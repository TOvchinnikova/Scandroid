package com.t_ovchinnikova.android.scandroid_2

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.t_ovchinnikova.android.scandroid_2.databinding.ActivityMainBinding
import com.t_ovchinnikova.android.scandroid_2.ui.ScanningFragment
import com.t_ovchinnikova.android.scandroid_2.ui.ScanningHistoryFragment

class MainActivity : AppCompatActivity(), Navigation {

    private lateinit var binding: ActivityMainBinding
    private lateinit var requestPermissionLauncher : ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    binding.bottomNavigationView.selectedItemId = R.id.menu_scanner
                } else {
                    Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                    //finish()
                }
            }

        requestPermissionLauncher.launch(Manifest.permission.CAMERA)

    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.menu_scanner -> showScanner()
                R.id.menu_history -> showHistoryList()
            }

            true
        }
    }

    override fun showScanner() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        //if (currentFragment == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, ScanningFragment.newInstance())
                .commit()
        //}
    }

    override fun showHistoryList() {
        Log.d("MyLog", "showHistoryList")
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        //if(currentFragment == null) {
            Log.d("MyLog", "showHistoryList11111")
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ScanningHistoryFragment.newInstance())
                .commit()
        //}
    }

    override fun showCodeGeneration() {
        TODO("Not yet implemented")
    }

    override fun showSettings() {
        TODO("Not yet implemented")
    }
}