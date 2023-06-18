package com.example.cnc.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import com.example.cnc.R
import com.example.cnc.databinding.ActivityMainBinding
import com.example.cnc.presentation.favouritesScreen.FavouritesScreenFragment
import com.example.cnc.presentation.friendsScreen.FriendsScreenFragment
import com.example.cnc.presentation.homeScreen.HomeScreenFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.bottomNavigation.background = null
        binding.bottomNavigation.menu.getItem(2).isEnabled = false
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.miFavourites -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FavouritesScreenFragment()).commit()

                R.id.miFriends -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FriendsScreenFragment()).commit()

                R.id.miChat -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, HomeScreenFragment()).commit()
            }
            true
        }// end onCreate
    }
}