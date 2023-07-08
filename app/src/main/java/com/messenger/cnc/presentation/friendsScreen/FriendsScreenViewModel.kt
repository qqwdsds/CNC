package com.messenger.cnc.presentation.friendsScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.data.models.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FriendsScreenViewModel : ViewModel() {
    // database instances
    private val firebaseAuth = Firebase.auth
    private val firebaseDatabase = Firebase.database

    private val _userFriendsList = MutableLiveData<List<User>>()
    private val userFriendsList: LiveData<List<User>> = _userFriendsList

    fun getFriendsList() {
        Log.d("Here", "getFriendsList")
        viewModelScope.launch {
            val userFriendsFolder = firebaseDatabase.getReference(DATABASE_FRIENDS_FOLDER_PATH)
            Log.d("Here", "Get user friends folder reference: $userFriendsFolder")
            Log.d("Here", "Try to get users list")
            userFriendsFolder.child(firebaseAuth.uid!!).get()
                .addOnSuccessListener {userFriendsListSnapshot ->
                    Log.d("Here", "Try to get users. Success: ${userFriendsListSnapshot.value}")
                }
                .addOnFailureListener {
                    Log.d("Here", "Try to get users. Exception: ${it.message}")
                }
                .addOnCanceledListener {
                    Log.d("Here", "Try to get users. Cancel")
                }
        }
    }

    companion object {
        private const val DATABASE_FRIENDS_FOLDER_PATH = "users-friends"
    }
}