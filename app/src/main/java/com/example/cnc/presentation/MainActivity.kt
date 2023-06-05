package com.example.cnc.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cnc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }

    }// end onCreate
}