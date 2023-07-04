package com.messenger.cnc.presentation.signinScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.domain.ErrorState
import com.messenger.cnc.domain.PendingState
import com.messenger.cnc.domain.State
import com.messenger.cnc.domain.SuccessState
import com.messenger.cnc.presentation.signinScreen.models.LoginData
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    // firebase instances
    private val firebaseAuth = Firebase.auth

    private val _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State> = _stateLiveData

    fun signInUser(userLoginData: LoginData) {
        viewModelScope.launch {
            _stateLiveData.postValue(PendingState())

            firebaseAuth.signInWithEmailAndPassword(userLoginData.email, userLoginData.password)
                .addOnSuccessListener {
                    _stateLiveData.postValue(SuccessState())
                }
                .addOnFailureListener{
                    _stateLiveData.postValue(ErrorState(it))
                }
        }
    }
}