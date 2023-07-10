package com.messenger.cnc.presentation
import com.messenger.cnc.R
import com.messenger.cnc.presentation.LogRegActivity
import com.messenger.cnc.presentation.favouritesScreen.FavouritesScreenFragment
import com.messenger.cnc.presentation.friendsScreen.FriendsScreenFragment
import com.messenger.cnc.presentation.homeScreen.HomeScreenFragment



import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.databinding.ActivityMainBinding

fun log(message: String) {
    Log.d("Here", message)
}

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