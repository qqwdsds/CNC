package com.messenger.cnc.presentation.friendsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentFriendsScreenBinding
import com.messenger.cnc.presentation.models.User

class FriendsScreenFragment :Fragment(R.layout.fragment_friends_screen) {
    private lateinit var binding: FragmentFriendsScreenBinding
    private lateinit var adapter: FriendsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFriendsScreenBinding.inflate(inflater)
        adapter = FriendsAdapter()

        return binding.root
    }// end onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeWindowButton.setOnClickListener{
            findNavController().popBackStack()
        }

        setupFriendsList()

    }// end onViewCreated

    private fun setupFriendsList(){
        binding.rvFriends.adapter = adapter
        binding.rvFriends.layoutManager = LinearLayoutManager(requireContext())

        adapter.submitList(mutableListOf(
            User(0, "Vladik", "Chokoladik", ""),
            User(1, "Igor", "Yosip",""),
            User(2, "Me", "Rija mavpa","")))
    }// end setupFriendsList
}