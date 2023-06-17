package com.example.cnc.presentation.signinScreen

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cnc.R
import com.example.cnc.databinding.FragmentSignScreenBinding

class SignInScreenFragment : Fragment(R.layout.fragment_sign_screen) {
    private lateinit var binding: FragmentSignScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSignScreenBinding.inflate(inflater)
        return binding.root
    }// end onCreateView
    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvSetColorRegister()
        navigat()
        binding.btnLogin.setOnClickListener {
            if(binding.textInputLayoutPassword.editText?.text.toString().length<9)
                binding.textInputLayoutPassword.error = "More than 8 characters are required"
            checkPassword()
            checkEmail()
        }
    }
    private fun checkEmail() {
        val email = binding.textInputEmail.editText?.text.toString()
        if (!email.contains("@gmail.com")) {
            Toast.makeText(context, "Error 😬, such mail does not exist ", Toast.LENGTH_SHORT)
                .show()
        }
    }
    private fun checkPassword(){
        binding.textInputPassword.doOnTextChanged{
                text, start, before, count ->
            if(text!!.length<9){
                binding.textInputLayoutPassword.error = "More than 8 characters are required"
            }else if(text!!.length>9){
                binding.textInputLayoutPassword.error = null
            }
        }
    }
    private fun navigat(){
        binding.tvRegister.setOnClickListener{
            findNavController().navigate(R.id.action_signScreenFragment_to_registrationScreenFragment)
        }
        binding.tvForgotPassword.setOnClickListener{
            findNavController().navigate(R.id.action_signScreenFragment_to_forgotPasswordFragment)
        }
    }
    private fun tvSetColorRegister(){
        val spannableStringBuilder = SpannableStringBuilder("Don't have an account? Register")

        val colorSpan1 = ForegroundColorSpan(Color.BLACK)
        val colorSpan2 = ForegroundColorSpan(Color.parseColor("#99BC47"))

        spannableStringBuilder.setSpan(colorSpan1, 0, "Don't have an account?".length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.setSpan(colorSpan2, "Don't have an account?".length, "Don't have an account?".length +" Register".length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvRegister.text = spannableStringBuilder
    }
}