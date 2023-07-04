package com.messenger.cnc.presentation.registrationScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentRegistrationScreenBinding
import com.messenger.cnc.domain.base.BaseSignInRegisterFragment
import com.messenger.cnc.domain.ErrorState
import com.messenger.cnc.domain.PendingState
import com.messenger.cnc.domain.SuccessState
import com.messenger.cnc.domain.errors.UsernameIsNotAvailableException
import com.messenger.cnc.presentation.MainActivity

open class RegistrationScreenFragment : BaseSignInRegisterFragment() {
    private lateinit var binding: FragmentRegistrationScreenBinding
    override val viewModel by viewModels<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentRegistrationScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?) {
        super.onViewCreated(
            view,
            savedInstanceState)
        setupNavigation()
        setupViews()

        viewModel.registerUserStateLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is PendingState -> {
                    log("State is pending")
                    // disable all views
                    changeViewsState(binding.root, DISABLE)
                }
                is SuccessState -> {
                    log("State is success")

                    // start main messenger activity and close this one
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    activity?.finish()
                }
                is ErrorState -> {
                    log("State is error")

                    changeViewsState(binding.root, ENABLE)
                    when (state.error) {
                        is UsernameIsNotAvailableException -> {
                            binding.userNameEditTextLayout.error = state.error.errorMessage
                        }
                        else -> {
                            Toast.makeText(requireContext(), ERROR, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } // end observe
    } // end onViewCreated

    private fun setupNavigation() {
        binding.tvIHAALogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * Function for add listeners to views.
     */
    private fun setupViews() : Unit = with(binding) {
        btnRegister.setOnClickListener {
            if (checkDataValidation()) {
                // register user
                registerUser()
            }
        }

        userNameEditText.doOnTextChanged { _, _, _, _ ->
            if (userNameEditTextLayout.error != null)
                userNameEditTextLayout.error = null
        }
        emailEditText.doOnTextChanged { _, _, _, _ ->
            if (emailEditTextLayout.error != null)
                emailEditTextLayout.error = null
        }
        passwordEditText.doOnTextChanged { _, _, _, _ ->
            if (passwordEditTextLayout.error != null || passwordRepeatEditTextLayout.error != null) {
                passwordEditTextLayout.error = null
                passwordRepeatEditTextLayout.error = null
            }
        }
        passwordRepeatEditText.doOnTextChanged { _, _, _, _ ->
            if (passwordEditTextLayout.error != null || passwordRepeatEditTextLayout.error != null) {
                passwordEditTextLayout.error = null
                passwordRepeatEditTextLayout.error = null
            }
        }
    }

    private fun registerUser() {
        val username = binding.userNameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        viewModel.registerUser(username, email, password)
    }

    /**
     * Checking all need data validation: username, user password, user email.
     * Return true if all data is valid, false when data is not valid.
     */
    private fun checkDataValidation(): Boolean {
        val isUserNameValid = checkUserNameIsValid()
        val isPasswordValid = checkPasswordAndRepeatPasswordIsValid()
        val isEmailValid = checkEmailIsValid()

        return isUserNameValid && isPasswordValid && isEmailValid
    }

    /**
     * Check if username is valid.
     * Return true if username is valid, false when username is not valid.
     */
    private fun checkUserNameIsValid(): Boolean {
        val username = binding.userNameEditText.text.toString()
        var isValid = true
        if(username.isBlank()) {
            binding.userNameEditTextLayout.error = getString(R.string.field_empty_error)
            isValid = false
        }
            // TODO check if username is available

        return isValid
    }

    /**
     * Check if email is valid.
     * Return true if email is valid, false when email is not valid.
     */
    private fun checkEmailIsValid(): Boolean {
        val email = binding.emailEditText.text.toString()
        var isValid = true

        if (email.isBlank()) {
            binding.emailEditTextLayout.error = getString(R.string.field_empty_error)
            isValid = false
        }
        else if (!email.contains(getString(R.string.mail_domain))) {
            binding.emailEditTextLayout.error = getString(R.string.wrong_email)
            isValid = false
        }

        return isValid
    }

    /**
     * Check if password is valid.
     * Return true if password is valid, false when password is not valid.
     */
    private fun checkPasswordAndRepeatPasswordIsValid(): Boolean = with(binding) {
        val password = passwordEditText.text.toString()
        val repeatedPassword = passwordRepeatEditText.text.toString()
        var isValid = true

        // check password
        if (password.isBlank()) {
            passwordEditTextLayout.error = getString(R.string.field_empty_error)
            isValid = false
        }
        else if (password.length < 9) {
            passwordEditTextLayout.error = getString(R.string.small_password_error)
            isValid = false
        }

        // check repeated password
        if (repeatedPassword.isBlank()) {
            passwordRepeatEditTextLayout.error = getString(R.string.field_empty_error)
            isValid = false
        }
        else if (repeatedPassword.length < 9) {
            passwordRepeatEditTextLayout.error = getString(R.string.small_password_error)
            isValid = false
        }

        // check to equals
        if (isValid && password != repeatedPassword) {
            passwordEditTextLayout.error = getString(R.string.passwords_not_match_error)
            passwordRepeatEditTextLayout.error = getString(R.string.passwords_not_match_error)
            isValid = false
        }

        return isValid
    }

    private fun log(message: String) {
        Log.d(TAG, message)
    }
    companion object {
        private const val TAG = "RegistrationScreenFragment"
        private const val ERROR = "Error"
    }
}