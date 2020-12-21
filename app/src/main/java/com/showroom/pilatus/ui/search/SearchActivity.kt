package com.showroom.pilatus.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.ActivityMainBinding
import com.showroom.pilatus.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.searchView.requestFocus()

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}