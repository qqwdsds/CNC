package com.messenger.cnc.presentation.registrationScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.domain.errors.UsernameIsNotAvailableException
import com.messenger.cnc.data.models.User
import com.messenger.cnc.domain.state.ErrorResult
import com.messenger.cnc.domain.state.PendingResult
import com.messenger.cnc.domain.state.Result
import com.messenger.cnc.domain.state.SuccessResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegistrationViewModel : ViewModel() {
    // firebase instances
    private val firebaseAuth = Firebase.auth
    private val firebaseDatabase = Firebase.database(DATABASE_REFERENCE)

    private val _resultLiveData = MutableLiveData<Result<Nothing>>()
    val resultLiveData: LiveData<Result<Nothing>> = _resultLiveData

    fun registerUser(
        username: String,
        email: String,
        password: String) {
        viewModelScope.launch {
            _resultLiveData.postValue(PendingResult())

            // username validation
            val snapshot = firebaseDatabase.getReference("users/usernames").child(username).get()
            val usernameValidation = snapshot.await()
            Log.d(
                TAG,
                "Username validation username value: ${usernameValidation.value}")
            if (usernameValidation.value != null) {
                _resultLiveData.postValue(ErrorResult(UsernameIsNotAvailableException()))
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
                    _resultLiveData.postValue(ErrorResult(it))
                }
        }
    }

    private fun addUserToDataBase(
        userId: String,
        username: String) {
        // HACK for firebase
        val user = User(
            id = userId,
            name = username,
            description = "",
            image = "")

        val databaseUserNamesFolder = firebaseDatabase.getReference("users/usernames/$username")
        databaseUserNamesFolder.setValue(userId).addOnSuccessListener {
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
                _resultLiveData.postValue(SuccessResult())
            }.addOnFailureListener {
                Log.d(
                    TAG,
                    "User has`nt added to database")
                _resultLiveData.postValue(ErrorResult(it))
            }
    }

    companion object {
        private const val DATABASE_REFERENCE =
            "https://create-new-chat-default-rtdb.europe-west1.firebasedatabase.app/"
        private const val TAG = "RegistrationViewModel"
    }
}