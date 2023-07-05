package com.messenger.cnc.presentation.friendsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentFriendsScreenBinding
import com.messenger.cnc.data.models.User

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

        setupNavigation()

        setupFriendsList()

        binding.searchFriend.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                adapter.getFilter().filter(text)
                return false
            }

        })

    }// end onViewCreated

    private fun setupFriendsList(){
        binding.rvFriends.adapter = adapter
        binding.rvFriends.layoutManager = LinearLayoutManager(requireContext())

        adapter.setList(mutableListOf(
            User("", "Vladik", "Chokoladik", null),
            User("", "Igor", "Yosip", null),
            User("", "Me", "Rija mavpa", null)))
    }// end setupFriendsList

    private fun setupNavigation() {
        binding.closeWindowButton.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.addFriendButton.setOnClickListener {
            findNavController().navigate(R.id.action_friendsScreenFragment_to_findFrienndsScreenFragment)
        }
    }
}