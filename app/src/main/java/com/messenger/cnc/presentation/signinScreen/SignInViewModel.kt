package com.messenger.cnc.presentation.signinScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.domain.state.ErrorResult
import com.messenger.cnc.domain.state.PendingResult
import com.messenger.cnc.domain.state.Result
import com.messenger.cnc.domain.state.SuccessResult
import com.messenger.cnc.presentation.signinScreen.models.LoginData
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    // firebase instances
    private val firebaseAuth = Firebase.auth

    private val _resultLiveData = MutableLiveData<Result<Nothing>>()
    val resultLiveData: LiveData<Result<Nothing>> = _resultLiveData

    fun signInUser(userLoginData: LoginData) {
        viewModelScope.launch {
            _resultLiveData.postValue(PendingResult())

            firebaseAuth.signInWithEmailAndPassword(userLoginData.email, userLoginData.password)
                .addOnSuccessListener {
                    _resultLiveData.postValue(SuccessResult())
                }
                .addOnFailureListener{
                    _resultLiveData.postValue(ErrorResult(it))
                }
        }
    }
}