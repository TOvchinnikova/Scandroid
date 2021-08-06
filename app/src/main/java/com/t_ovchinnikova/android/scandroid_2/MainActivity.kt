package com.t_ovchinnikova.android.scandroid_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.t_ovchinnikova.android.scandroid_2.databinding.ActivityMainBinding
import com.t_ovchinnikova.android.scandroid_2.ui.ScanningFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) //Поиск текущего фрагмента

        if (currentFragment == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, ScanningFragment.newInstance())
                .commit()
        }

    }
}