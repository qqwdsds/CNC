package com.messenger.cnc.presentation.friendsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.data.models.User
import com.messenger.cnc.domain.state.PendingResult
import com.messenger.cnc.domain.state.Result
import com.messenger.cnc.domain.state.SuccessResult
import com.messenger.cnc.presentation.log
import kotlinx.coroutines.launch

class FriendsScreenViewModel : ViewModel() {
    private val _resultLiveData = MutableLiveData<Result<List<User>>>()
    val resultLiveData: LiveData<Result<List<User>>> = _resultLiveData

    // database instances
    private var auth: FirebaseAuth
    private var friendsDatabaseReference: DatabaseReference
    init {
        auth = Firebase.auth
        friendsDatabaseReference = Firebase.database(DATABASE_REFERENCE).getReference(DATABASE_FRIENDS_FOLDER_PATH + auth.uid)
        log("Get user list: firebase reference $friendsDatabaseReference")
    }

    fun getUsers() {
        // retrieve users
        _resultLiveData.postValue(PendingResult())
        viewModelScope.launch {
            log("Get user list: get users")
            friendsDatabaseReference.get().addOnSuccessListener { snapshot ->
                    log("Get user list: get users success")
                    val users = ArrayList<User>()
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        log("Get user list: (use get method) UserId = ${user?.id}")
                        users.add(user!!)
                    }
                    _resultLiveData.postValue(SuccessResult(users))
                }
        }
    }

    companion object {
        private const val DATABASE_FRIENDS_FOLDER_PATH = "users-friends/"
        private const val DATABASE_REFERENCE =
            "https://create-new-chat-default-rtdb.europe-west1.firebasedatabase.app/"
    }
}