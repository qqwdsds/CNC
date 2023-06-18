package com.example.cnc.presentation.signinScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cnc.R
import com.example.cnc.databinding.FragmentSignScreenBinding
import com.example.cnc.presentation.MainActivity

class SignInScreenFragment : Fragment(R.layout.fragment_sign_screen) {
    private lateinit var binding: FragmentSignScreenBinding

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
        navigate()
        checkLogin()
    }

    private fun checkLogin() {
        binding.btnLogin.setOnClickListener {
            val inputPassword = binding.textInputLayoutPassword.editText?.text.toString()
            val inputEmail = binding.textInputEmail.editText?.text.toString()

            if (inputPassword.length < 9) {
                binding.textInputLayoutPassword.error = getString(R.string.small_password_error)

                binding.textInputPassword.doOnTextChanged { text, start, before, count ->
                    if (text!!.length < 9) {
                        binding.textInputLayoutPassword.error =
                            getString(R.string.small_password_error)
                    }
                    else if (text!!.length > 9) {
                        binding.textInputLayoutPassword.error = null
                    }
                }
            }
            else if (!inputEmail.contains(getString(R.string.mail_domain))) {
                Toast.makeText(
                    context,
                    getString(R.string.mail_not_exist_error),
                    Toast.LENGTH_SHORT).show()
                binding.textInputEmail.error = getString(R.string.wrong_email)
            }
            else {
                startActivity(
                    Intent(
                        requireContext(),
                        MainActivity::class.java))
                activity?.finish()
            }
        }

    } // end checkLogin

    private fun navigate() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signScreenFragment_to_registrationScreenFragment)
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_signScreenFragment_to_forgotPasswordFragment)
        }
    } // end navigate
}