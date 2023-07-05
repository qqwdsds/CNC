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
import com.messenger.cnc.domain.errors.UsernameIsNotAvailableException
import com.messenger.cnc.data.models.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegistrationViewModel : ViewModel() {
    // firebase instances
    private val firebaseAuth = Firebase.auth
    private val firebaseDatabase = Firebase.database(DATABASE_REFERENCE)

    private val _registerUserStateLiveData = MutableLiveData<State>()
    val registerUserStateLiveData: LiveData<State> = _registerUserStateLiveData

    fun registerUser(
        username: String,
        email: String,
        password: String) {
        viewModelScope.launch {
            _registerUserStateLiveData.postValue(PendingState())

            // username validation
            val snapshot = firebaseDatabase.getReference("users/usernames").child(username).get()
            val usernameValidation = snapshot.await()
            if (usernameValidation.value != null) {
                _registerUserStateLiveData.postValue(ErrorState(UsernameIsNotAvailableException()))
                return@launch
            }

            Log.d(
                TAG,
                "Try to register user")
            firebaseAuth.createUserWithEmailAndPassword(
                email,
                password).addOnSuccessListener {
                    Log.d(
                        TAG,
                        "Register user is success")
                    val userUid = it.user?.uid
                    if (userUid != null) {
                        addUserToDataBase(
                            userUid,
                            username)
                    }
                }.addOnFailureListener {
                    Log.d(
                        TAG,
                        "Register user is failure")
                    _registerUserStateLiveData.postValue(ErrorState(it))
                }
        }
    }

    private fun addUserToDataBase(
        userId: String,
        username: String) {
        val user = User(
            id = userId,
            name = username,
            description = null,
            image = null)


        val databaseUserNamesFolder = firebaseDatabase.getReference("users/usernames/$username")
        databaseUserNamesFolder.setValue(username).addOnSuccessListener {
            Log.d(
                TAG,
                "Username has been added to database")
            }.addOnFailureListener {
                Log.d(
                    TAG,
                    "Username has`nt added to database")
            }

        Log.d(
            TAG,
            "Try to add user to database")
        val databaseUsersFolderRef = firebaseDatabase.getReference("users/$userId")
        databaseUsersFolderRef.setValue(user).addOnSuccessListener {
                Log.d(
                    TAG,
                    "User has been added to database")
                _registerUserStateLiveData.postValue(SuccessState())
            }.addOnFailureListener {
                Log.d(
                    TAG,
                    "User has`nt added to database")
                _registerUserStateLiveData.postValue(ErrorState(it))
            }
    }

    companion object {
        private const val DATABASE_REFERENCE =
            "https://create-new-chat-default-rtdb.europe-west1.firebasedatabase.app/"
        private const val TAG = "RegistrationViewModel"
    }
}