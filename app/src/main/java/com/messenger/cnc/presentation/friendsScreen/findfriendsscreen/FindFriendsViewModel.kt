package com.messenger.cnc.presentation.friendsScreen.findfriendsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.data.models.User
import com.messenger.cnc.domain.errors.UserNotFoundException
import com.messenger.cnc.domain.state.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FindFriendsViewModel: ViewModel() {
    // firebase instances
    private val firebaseAuth = Firebase.auth
    private val firebaseDatabase = Firebase.database(DATABASE_REFERENCE)

    private val _searchUserLiveData = MutableLiveData<Result<User>>()
    val searchUserLiveData: LiveData<Result<User>> = _searchUserLiveData

    private val _addFriendLiveData = MutableLiveData<Result<Nothing>>()
    val addFriendLiveData: LiveData<Result<Nothing>> = _addFriendLiveData

    fun searchUser(username: String) {
        if (_searchUserLiveData.value !is PendingResult) {
            viewModelScope.launch {
                _searchUserLiveData.postValue(PendingResult())

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
                                    _searchUserLiveData.postValue(SuccessResult(userInstance))
                                } catch (e: Exception) {
                                    _searchUserLiveData.postValue(ErrorResult(UserNotFoundException()))
                                }
                            }
                    } else {
                        _searchUserLiveData.postValue(ErrorResult(UserNotFoundException()))
                    }
                }
            }
        }
    } // end searchUser

    // trash implementation, don`t do like that
    fun addFriend(userId: String) {
        viewModelScope.launch {
            _addFriendLiveData.postValue(PendingResult())
            // get references to database folders
            val usersFolder = firebaseDatabase.getReference(DATABASE_USERS_FOLDER_PATH)
            val usersFriendsFolder = firebaseDatabase.getReference(String.format(DATABASE_FRIENDS_FOLDER_PATH, firebaseAuth.uid, userId))

            // get user from database
            val userSnapshot = usersFolder.child(userId).get().await()
            val user = userSnapshot.getValue(User::class.java)

            // set user to users friends folder
            usersFriendsFolder.setValue(user)
                .addOnSuccessListener {
                    _addFriendLiveData.postValue(SuccessResult())
                }
                .addOnFailureListener {
                    _addFriendLiveData.postValue(ErrorResult(it))
                }
        }
    }

    companion object {
        private const val DATABASE_REFERENCE =
            "https://create-new-chat-default-rtdb.europe-west1.firebasedatabase.app/"
        private const val DATABASE_USERS_FOLDER_PATH = "users"
        private const val DATABASE_USERNAMES_FOLDER_PATH = "users/usernames"
        private const val DATABASE_FRIENDS_FOLDER_PATH = "users-friends/%s/%s"
        private const val TAG = "FindFriendsViewModel"
    }
}