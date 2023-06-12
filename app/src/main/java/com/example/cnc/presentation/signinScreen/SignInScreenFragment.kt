package com.example.cnc.presentation.signinScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cnc.R
import com.example.cnc.databinding.FragmentSignScreenBinding
import com.example.cnc.presentation.MainActivity

class SignInScreenFragment : Fragment(R.layout.fragment_sign_screen) {
    private lateinit var binding: FragmentSignScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSignScreenBinding.inflate(inflater)
        return binding.root
    }// end onCreateView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnSignIn.setOnClickListener {
                //findNavController().navigate(R.id.action_signScreenFragment2_to_homeScreenFragment)

                val mainActivity = Intent(requireContext(), MainActivity::class.java)
                startActivity(mainActivity)
                activity?.finish()
            }
            btnRegisterNow.setOnClickListener {
                findNavController().navigate(R.id.action_signScreenFragment_to_registrationScreenFragment2)
            }
        }
    }
}