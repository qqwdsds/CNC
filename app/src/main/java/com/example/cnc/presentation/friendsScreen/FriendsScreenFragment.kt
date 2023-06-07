package com.example.cnc.presentation.friendsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cnc.R
import com.example.cnc.databinding.FragmentFriendsScreenBinding

class FriendsScreenFragment :Fragment(R.layout.fragment_friends_screen) {
    private lateinit var binding: FragmentFriendsScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFriendsScreenBinding.inflate(inflater)

        return binding.root
    }// end onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeWindowButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }// end onViewCreated
}