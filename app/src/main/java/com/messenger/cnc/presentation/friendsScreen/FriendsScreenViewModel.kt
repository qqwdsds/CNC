package com.messenger.cnc.presentation.friendsScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.data.models.User
import com.messenger.cnc.domain.PendingState
import com.messenger.cnc.domain.State
import com.messenger.cnc.domain.SuccessState

class FriendsScreenViewModel : ViewModel() {
    private val _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State> = _stateLiveData

    private val _userFriendsListLiveData = MutableLiveData<List<User>>()
    val userFriendsListLiveData: LiveData<List<User>> = _userFriendsListLiveData

    // database instances
    private var auth: FirebaseAuth
    private var friendsDatabaseReference: DatabaseReference
    init {
        auth = Firebase.auth
        friendsDatabaseReference = Firebase.database.getReference("users-friends/${auth.uid}")
    }

    fun getUsers() {
        // retrieve users
        // TODO when users is not downloaded - show progressbar
        _stateLiveData.postValue(PendingState())
        friendsDatabaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = ArrayList<User>()
                for (userSnapshot in snapshot.getChildren()) {
                    val user = userSnapshot.getValue(User::class.java)
                    Log.d("FriendsScreenViewModel", "Got user: ${user?.id}")
                    if (user != null) {
                        users.add(user)
                    }
                    _stateLiveData.postValue(SuccessState())
                    _userFriendsListLiveData.postValue(users)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Error message: ${error.message}")
            }
        })
    }

    companion object {
        private const val DATABASE_FRIENDS_FOLDER_PATH = "users-friends"
        private const val TAG = "FriendsScreenViewModel"
    }
}