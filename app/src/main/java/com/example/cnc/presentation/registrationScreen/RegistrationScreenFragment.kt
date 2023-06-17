package com.example.cnc.presentation.registrationScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cnc.R
import com.google.android.material.textfield.TextInputLayout
import com.example.cnc.databinding.FragmentRegistrationScreenBinding
import com.example.cnc.presentation.MainActivity

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
        tvSetColorLogin()
        binding.tvIHAALogin.setOnClickListener {
            findNavController().popBackStack()
        }
        isCheckedField()
    }

    private fun tvSetColorLogin() {
        val spannableStringBuilder = SpannableStringBuilder("I have an account? Log in")

        val colorSpan1 = ForegroundColorSpan(Color.BLACK)
        val colorSpan2 = ForegroundColorSpan(Color.parseColor("#99BC47"))

        spannableStringBuilder.setSpan(
            colorSpan1,
            0,
            "I have an account?".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.setSpan(
            colorSpan2,
            "I have an account?".length,
            "I have an account?".length + " Log in".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvIHAALogin.text = spannableStringBuilder
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
                        "This field is empty " else {textInputFirstName.error = null
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
        if (!email.contains("@gmail.com") || email.isEmpty())
            binding.textInputEmail.error = "Email is wrong"
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
                textInputLayoutPassword.error = "Upps 🙁, your Passwords do not match"
                textInputLayoutRepeatPassword.error = "Upps 🙁, your Passwords do not match"
            }else
            if (text1.length < 9 && text2.length < 9){
                textInputLayoutPassword.error = "More than 8 characters are required"
                textInputLayoutRepeatPassword.error = "More than 8 characters are required"}
            else result =true

            textInputPassword.doOnTextChanged { text, start, before, count ->
                if (text!!.length < 9) {
                    binding.textInputLayoutPassword.error = "More than 8 characters are required"
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
                        "More than 8 characters are required"
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