package com.example.cnc.presentation.homeScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cnc.R
import com.example.cnc.databinding.FragmentHomeScreenBinding

private val TAG = "Here"
class HomeScreenFragment: Fragment(R.layout.fragment_home_screen) {
    private lateinit var binding: FragmentHomeScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater)

        return binding.root
    }// end onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBtnChatAll.setOnClickListener{
            Log.d(TAG, "try to open chats fragment")
            findNavController().navigate(R.id.action_homeScreenFragment_to_chatsFragment, )
        }

        binding.imgBtnFavourites.setOnClickListener{
            findNavController().navigate(R.id.action_homeScreenFragment_to_favouritesScreenFragment)
        }

        binding.imgBtnFriends.setOnClickListener{
            findNavController().navigate(R.id.action_homeScreenFragment_to_friendsScreenFragment)
        }
        binding.imgBtnOff.setOnClickListener{
            findNavController().navigate(R.id.action_homeScreenFragment_to_signScreenFragment2)
        }
    }// end onViewCreated
}