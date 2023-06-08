package com.example.cnc.presentation.registrationScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cnc.R
import com.example.cnc.databinding.FragmentRegistrationScreenBinding

class RegistrationScreenFragment : Fragment(R.layout.fragment_registration_screen) {
    private lateinit var binding : FragmentRegistrationScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = com.example.cnc.databinding.FragmentRegistrationScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnRegister.setOnClickListener {
                findNavController().popBackStack()
            }
            btnSign.setOnClickListener {
                findNavController().navigate(R.id.action_registrationScreenFragment_to_homeScreenFragment)
            }
        }
    }
}