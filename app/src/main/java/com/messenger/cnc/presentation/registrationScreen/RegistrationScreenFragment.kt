package com.messenger.cnc.presentation.registrationScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentRegistrationScreenBinding
import com.messenger.cnc.presentation.MainActivity

class RegistrationScreenFragment : Fragment(R.layout.fragment_registration_screen) {
    private lateinit var binding: FragmentRegistrationScreenBinding

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
    }

    private fun setupNavigation() {
        binding.tvIHAALogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupViews() = with(binding) {
        firstNameEditText.doOnTextChanged { _, _, _, _ ->
            if (firstNameEditTextLayout.error != null)
                firstNameEditTextLayout.error = null
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

        btnRegister.setOnClickListener {
            if (checkDataValidation()) {
                startActivity(
                    Intent(
                        requireContext(),
                        MainActivity::class.java))
                activity?.finish()
            }
        }
    }

    private fun checkDataValidation(): Boolean {
        val isFirstNameValid = checkFirsNameIsValid()
        val isPasswordValid = checkPasswordAndRepeatPasswordIsValid()
        val isEmailValid = checkEmailIsValid()

        return isFirstNameValid && isPasswordValid && isEmailValid
    }

    private fun checkFirsNameIsValid(): Boolean {
        val firstName = binding.firstNameEditText.text.toString()
        var isValid = true
        if(firstName.isBlank()) {
            binding.firstNameEditTextLayout.error = getString(R.string.field_empty_error)
            isValid = false
        }
        return isValid
    }

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
}