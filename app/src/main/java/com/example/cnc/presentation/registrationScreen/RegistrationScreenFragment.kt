package com.example.cnc.presentation.registrationScreen

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cnc.R
import com.example.cnc.databinding.FragmentRegistrationScreenBinding
import com.example.cnc.presentation.MainActivity

class RegistrationScreenFragment : Fragment(R.layout.fragment_registration_screen) {
    private lateinit var binding : FragmentRegistrationScreenBinding

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
    }
    private fun tvSetColorLogin(){
        val spannableStringBuilder = SpannableStringBuilder("I have an account? Log in")

        val colorSpan1 = ForegroundColorSpan(Color.BLACK)
        val colorSpan2 = ForegroundColorSpan(Color.parseColor("#99BC47"))

        spannableStringBuilder.setSpan(colorSpan1, 0, "I have an account?".length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.setSpan(colorSpan2, "I have an account?".length, "I have an account?".length +" Log in".length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvIHAALogin.text = spannableStringBuilder
    }
}