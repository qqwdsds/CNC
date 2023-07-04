package com.messenger.cnc.presentation.registrationScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.domain.ErrorState
import com.messenger.cnc.domain.PendingState
import com.messenger.cnc.domain.State
import com.messenger.cnc.domain.SuccessState
import com.messenger.cnc.presentation.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationViewModel: ViewModel() {
    // firebase instances
    private val firebaseAuth = Firebase.auth
    private val firebaseDatabase = Firebase.database(DATABASE_REFERENCE)

    private val _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State> = _stateLiveData

    fun registerUser(username: String, email: String, password: String) {
        // maybe trash

        viewModelScope.launch {
            _stateLiveData.postValue(PendingState())
            Log.d(
                "Here",
                "Try to register user")
            firebaseAuth.createUserWithEmailAndPassword(
                email,
                password)
                .addOnSuccessListener {
                    Log.d(
                        "Here",
                        "Register user is success")
                    val userUid = it.user?.uid
                    if (userUid != null) {
                        addUserToDataBase(
                            userUid,
                            username)
                    }
                }.addOnFailureListener {
                    Log.d(
                        "Here",
                        "Register user is failure")
                    _stateLiveData.postValue(ErrorState(it))
                }
        }
    }
    private fun addUserToDataBase(userId: String, username: String) {
        val user = User(
            id = userId,
            name = username,
            description = null,
            image = null
        )

        Log.d(
            "Here",
            "Try to add user to database")
        val dataBaseFolderReference = firebaseDatabase.getReference("user/$userId")
        dataBaseFolderReference.setValue(user)
            .addOnSuccessListener {
                Log.d(
                    "Here",
                    "Add user to database: Success")
                _stateLiveData.postValue(SuccessState())
            }
            .addOnFailureListener {
                Log.d(
                    "Here",
                    "Add user to database: Failure")
                _stateLiveData.postValue(ErrorState(it))
            }
    }

    companion object {
        private const val DATABASE_REFERENCE = "https://create-new-chat-default-rtdb.europe-west1.firebasedatabase.app/"
    }
}