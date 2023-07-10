package com.messenger.cnc.presentation.homeScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentHomeScreenBinding
import com.messenger.cnc.presentation.LogRegActivity

private val TAG = "Here"
class HomeScreenFragment: Fragment(R.layout.fragment_home_screen) {
    private lateinit var binding: FragmentHomeScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater)

        return binding.root
    }// end onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }// end onViewCreated
    


    }// end onViewCreated
