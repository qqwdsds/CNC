package com.messenger.cnc.presentation.registrationScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentRegistrationScreenBinding
import com.messenger.cnc.domain.BaseFragment
import com.messenger.cnc.domain.ErrorState
import com.messenger.cnc.domain.PendingState
import com.messenger.cnc.domain.SuccessState
import com.messenger.cnc.presentation.LogRegActivity
import com.messenger.cnc.presentation.MainActivity

class RegistrationScreenFragment : BaseFragment() {
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

        viewModel.stateLiveData.observe(viewLifecycleOwner) {state ->
            when(state) {
                is PendingState -> {
                    Log.d(
                        "Here",
                        "State is pending")

                    // show progressbar and eclipse background
                    binding.progressbar.visibility = View.VISIBLE
                    binding.backgroundEclipse.visibility = View.VISIBLE

                    changeViewsState(DISABLE)
                }
                is SuccessState -> {
                    Log.d(
                        "Here",
                        "State is success")

                    // hide progressbar and background eclipse
                    binding.progressbar.visibility = View.GONE
                    binding.backgroundEclipse.visibility = View.GONE

                    changeViewsState(ENABLE)

                    // start main messenger activity and close this one
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    activity?.finish()
                }
                is ErrorState -> {
                    // TODO

                    // hide progressbar and background eclipse
                    binding.progressbar.visibility = View.GONE
                    binding.backgroundEclipse.visibility = View.GONE

                    changeViewsState(ENABLE)
                    throw state.error
                }
            }
        }
    }

    private fun setupNavigation() {
        binding.tvIHAALogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupViews() = with(binding) {
        btnRegister.setOnClickListener {
            if (checkDataValidation()) {
                // disable views
                changeViewsState(DISABLE)

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
     * Disable all view on screen.
     * state:
     * [RegistrationScreenFragment.DISABLE] - disable all views;
     * [RegistrationScreenFragment.ENABLE] - enable all view
     */
    private fun changeViewsState(state: Int) {
        when (state) {
            DISABLE -> {
                binding.root.children.forEach {
                    it.isEnabled = false
                }
            }
            ENABLE -> {
                binding.root.children.forEach {
                    it.isEnabled = true
                }
            }
        }
    }

    /**
     * Checking all need data validation: username, user password, user email.
     * Return true if all data is valid, false when data is not valid.
     */
    private fun checkDataValidation(): Boolean {
        val isFirstNameValid = checkUserNameIsValid()
        val isPasswordValid = checkPasswordAndRepeatPasswordIsValid()
        val isEmailValid = checkEmailIsValid()

        return isFirstNameValid && isPasswordValid && isEmailValid
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
        Log.d(
            TAG,
            message)
    }
    companion object {
        // to disable/enable views
        private const val DISABLE = 0
        private const val ENABLE = 1

        private const val TAG = "RegistrationScreenFragment"
    }
}