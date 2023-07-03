package com.messenger.cnc.presentation.signinScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentSignScreenBinding
import com.messenger.cnc.domain.BaseFragment
import com.messenger.cnc.presentation.MainActivity

class SignInScreenFragment : BaseFragment() {
    private lateinit var binding: FragmentSignScreenBinding
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentSignScreenBinding.inflate(inflater)
        return binding.root
    } // end onCreateView

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?) {
        super.onViewCreated(
            view,
            savedInstanceState)
        setupNavigation()
        setupViews()
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
            if(checkDataValidation()) {
                startActivity(
                    Intent(
                        requireContext(),
                        MainActivity::class.java))
                activity?.finish()
            }
        }

    } // end checkLogin

    private fun checkDataValidation(): Boolean {
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

        return isValid
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