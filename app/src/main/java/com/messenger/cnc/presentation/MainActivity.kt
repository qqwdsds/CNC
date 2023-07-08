package com.messenger.cnc.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }

        // TODO if user was deleted
        // check user authentication
        val auth = Firebase.auth
        if(auth.currentUser == null) {
            startActivity(Intent(this, LogRegActivity::class.java))
        }

    }// end onCreate
}