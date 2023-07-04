package com.messenger.cnc.presentation.signinScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentSignScreenBinding
import com.messenger.cnc.domain.BaseSignInRegisterFragment
import com.messenger.cnc.domain.ErrorState
import com.messenger.cnc.domain.PendingState
import com.messenger.cnc.domain.SuccessState
import com.messenger.cnc.presentation.MainActivity
import com.messenger.cnc.presentation.signinScreen.models.LoginData
import com.messenger.cnc.presentation.signinScreen.models.ValidationResult

class SignInScreenFragment : BaseSignInRegisterFragment() {
    private lateinit var binding: FragmentSignScreenBinding
    override val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentSignScreenBinding.inflate(inflater)
        return binding.root
    } // end onCreateView

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?) {
        super.onViewCreated(
            view,
            savedInstanceState)
        setupNavigation()
        setupViews()

        viewModel.stateLiveData.observe(viewLifecycleOwner) {state ->
            when (state) {
                is PendingState -> {
                    changeViewsState(binding.root, DISABLE)
                }
                is SuccessState -> {
                    // start main messenger activity and close this one
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    activity?.finish()
                }
                is ErrorState -> {
                    // TODO
                    changeViewsState(binding.root, ENABLE)
                    Toast.makeText(requireContext(), state.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupViews() {
        binding.passwordEditText.doOnTextChanged { _, _, _, _ ->
            if (binding.passwordEditTextLayout.error != null)
                binding.passwordEditTextLayout.error = null
        }

        binding.emailEditText.doOnTextChanged { _, _, _, _ ->
            if (binding.emailEditTextLayout.error != null)
                binding.emailEditTextLayout.error = null
        }

        binding.btnLogin.setOnClickListener {
            val dataValidationResult = checkDataValidation()
            if(dataValidationResult.result) {
                //login user
                viewModel.signInUser(dataValidationResult.data!!)
            }
        }

    } // end checkLogin

    private fun checkDataValidation(): ValidationResult {
        val inputEmail = binding.emailEditText.text.toString()
        val inputPassword = binding.passwordEditText.text.toString()
        var isValid = true

        // email
        if (inputEmail.isBlank()) {
            binding.emailEditTextLayout.error = getString(R.string.field_empty_error)
            isValid = false
        }
        else if (!inputEmail.contains(getString(R.string.mail_domain))) {
            binding.emailEditTextLayout.error = getString(R.string.wrong_email)
            isValid = false
        }

        // password
        if (inputPassword.isBlank()){
            binding.passwordEditTextLayout.error = getString(R.string.field_empty_error)
            isValid = false
        }
        else if (inputPassword.length < 9) {
            binding.passwordEditTextLayout.error = getString(R.string.small_password_error)
            isValid = false
        }

        return if (isValid) {
            ValidationResult(isValid, LoginData(inputEmail, inputPassword))
        } else {
            ValidationResult(isValid)
        }
    }

    private fun setupNavigation() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signScreenFragment_to_registrationScreenFragment)
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_signScreenFragment_to_forgotPasswordFragment)
        }
    } // end navigate
}