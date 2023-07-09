package com.messenger.cnc.presentation.friendsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentFriendsScreenBinding
import com.messenger.cnc.domain.base.BaseFragment

class FriendsScreenFragment: BaseFragment() {
    private lateinit var binding: FragmentFriendsScreenBinding
    private lateinit var adapter: FriendsAdapter
    override val viewModel by viewModels<FriendsScreenViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFriendsScreenBinding.inflate(inflater)
        adapter = FriendsAdapter()

        return binding.root
    }// end onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()

        setupFriendsList()

        // TODO: PendingState - show progressbar, SuccessState - get list from this object and disable progressbar
        viewModel.userFriendsListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

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

    private fun setupFriendsList() {
        binding.rvFriends.adapter = adapter
        binding.rvFriends.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getUsers()

        // TODO mock
        /*adapter.setList(mutableListOf(
            User("", "Vladik", "Chokoladik", ""),
            User("", "Igor", "Yosip", ""),
            User("", "Me", "Rija mavpa", "")))*/

    }// end setupFriendsList

    private fun setupNavigation() {
        binding.closeWindowButton.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.addFriendButton.setOnClickListener {
            findNavController().navigate(R.id.action_friendsScreenFragment_to_findFrienndsScreenFragment)
        }
    }

    companion object {
        private const val TAG = "FriendsScreenFragment"
    }
}