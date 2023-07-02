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
import com.google.android.material.textfield.TextInputLayout
import com.messenger.cnc.databinding.FragmentRegistrationScreenBinding
import com.messenger.cnc.presentation.MainActivity

class RegistrationScreenFragment : Fragment(R.layout.fragment_registration_screen) {
    private lateinit var binding: FragmentRegistrationScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvIHAALogin.setOnClickListener {
            findNavController().popBackStack()
        }
        isCheckedField()
    }
    private fun isCheckedField() {
        var result =false
        with(binding) {
            btnRegister.setOnClickListener {
                if (checkPasswordRepeatPassword() && checkEmail() && result) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    activity?.finish()
                } else {
                    if (isTextInputLayoutEmpty(textInputFirstName)) textInputFirstName.error =
                        getString(R.string.field_empty_error) else {textInputFirstName.error = null
                        result= true}
                    checkPasswordRepeatPassword()
                    checkEmail()
                }
            }
        }
    }

    private fun checkEmail(): Boolean {
        var result = false
        val email = binding.textInputEmail.editText?.text.toString()
        if (!email.contains(getString(R.string.mail_domain)) || email.isEmpty())
            binding.textInputEmail.error = getString(R.string.wrong_email)
        else {
            binding.textInputEmail.error = null
            result = true
        }
        return result
    }

    private fun checkPasswordRepeatPassword(): Boolean {
        var result = false
        binding.apply {
            val text1 = textInputLayoutPassword.editText?.text.toString()
            val text2 = textInputLayoutRepeatPassword.editText?.text.toString()
            if (text1 != text2) {
                textInputLayoutPassword.error = getString(R.string.passwords_not_match_error)
                textInputLayoutRepeatPassword.error = getString(R.string.passwords_not_match_error)
            }else
            if (text1.length < 9 && text2.length < 9){
                textInputLayoutPassword.error = getString(R.string.small_password_error)
                textInputLayoutRepeatPassword.error = getString(R.string.small_password_error)}
            else result =true

            textInputPassword.doOnTextChanged { text, start, before, count ->
                if (text!!.length < 9) {
                    binding.textInputLayoutPassword.error = getString(R.string.small_password_error)
                } else {
                    if (text.length > 8) {
                        binding.textInputLayoutPassword.error = null
                        result = true
                    }
                }
            }
            textInputRepeatPassword.doOnTextChanged { text, start, before, count ->
                if (text!!.length < 9) {
                    binding.textInputLayoutRepeatPassword.error =
                        getString(R.string.small_password_error)
                } else {
                    if (text.length > 8) {
                        binding.textInputLayoutRepeatPassword.error = null
                        result = true
                    }
                }
            }
            return result
        }
    }

    fun isTextInputLayoutEmpty(textInputLayout: TextInputLayout): Boolean {
        val editText = textInputLayout.editText
        val text = editText?.text?.toString()
        return text.isNullOrBlank()
    }
}