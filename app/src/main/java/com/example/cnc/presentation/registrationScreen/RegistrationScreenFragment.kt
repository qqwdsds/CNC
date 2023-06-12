package com.example.cnc.presentation.registrationScreen

import android.content.Intent
import android.os.Bundle
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
        with(binding){
            btnRegister.setOnClickListener {
                val mainActivity = Intent(requireContext() ,MainActivity::class.java)
                startActivity(mainActivity)
                activity?.finish()
            }
            btnSign.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}