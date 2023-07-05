package com.messenger.cnc.presentation.friendsScreen.findfriendsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.data.models.User
import com.messenger.cnc.domain.ErrorState
import com.messenger.cnc.domain.PendingState
import com.messenger.cnc.domain.State
import com.messenger.cnc.domain.SuccessState
import com.messenger.cnc.domain.errors.UserNotFoundException
import kotlinx.coroutines.launch
import java.lang.Exception

class FindFriendsViewModel: ViewModel() {
    // firebase instances
    private val firebaseDatabase = Firebase.database(DATABASE_REFERENCE)

    private val _searchLiveData = MutableLiveData<User>()
    val searchLiveData: LiveData<User> = _searchLiveData

    private val _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State> = _stateLiveData

    fun searchUser(username: String) {
        if (_stateLiveData.value !is PendingState) {
            viewModelScope.launch {
                _stateLiveData.postValue(PendingState())

                // get userId from database
                // if user doesn't exist DataSnapshot.value will be null
                firebaseDatabase.getReference(DATABASE_USERNAMES_FOLDER_PATH).child(username).get()
                    .addOnSuccessListener {
                    val userId = it.value
                    if (userId != null) {
                        // get user from database
                        firebaseDatabase.getReference(DATABASE_USERS_FOLDER_PATH).child(userId.toString()).get()
                            .addOnSuccessListener {user ->
                                try {
                                    val userInstance = user.getValue(User::class.java)
                                    _searchLiveData.postValue(userInstance!!)
                                    _stateLiveData.postValue(SuccessState())
                                } catch (e: Exception) {
                                    _stateLiveData.postValue(ErrorState(UserNotFoundException()))
                                }
                            }
                    } else {
                        _stateLiveData.postValue(ErrorState(Exception("User not found")))
                    }
                }
            }
        }
    }

    companion object {
        private const val DATABASE_REFERENCE =
            "https://create-new-chat-default-rtdb.europe-west1.firebasedatabase.app/"
        private const val DATABASE_USERS_FOLDER_PATH = "users"
        private const val DATABASE_USERNAMES_FOLDER_PATH = "users/usernames"
        private const val TAG = "FindFriendsViewModel"
    }
}