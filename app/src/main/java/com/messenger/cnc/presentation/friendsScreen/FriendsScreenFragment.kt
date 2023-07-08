package com.messenger.cnc.presentation.friendsScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentFriendsScreenBinding
import com.messenger.cnc.data.models.User
import com.messenger.cnc.domain.base.BaseFragment
import com.messenger.cnc.presentation.friendsScreen.findfriendsscreen.FindFriendsScreenFragment

class FriendsScreenFragment: BaseFragment() {
    // database instances
    private lateinit var auth: FirebaseAuth
    private lateinit var friendsDatabaseReference: DatabaseReference

    private lateinit var binding: FragmentFriendsScreenBinding
    private lateinit var adapter: FriendsAdapter
    override val viewModel by viewModels<FriendsScreenViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFriendsScreenBinding.inflate(inflater)
        adapter = FriendsAdapter()

        auth = Firebase.auth
        friendsDatabaseReference = Firebase.database.getReference("users-friends/${auth.uid}")

        Log.d(
            "User",
            "Auth.uid: ${auth.uid}")

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

    private fun setupFriendsList() {
        binding.rvFriends.adapter = adapter
        binding.rvFriends.layoutManager = LinearLayoutManager(requireContext())

        Log.d(
            "User",
            "Users friends folder reference: ${friendsDatabaseReference}")

        friendsDatabaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    Log.d(
                        "User",
                        "User snapshot.getValue(User::class.java): ${user}")
                    if (user != null) {
                        adapter.setUser(user)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO("Not yet implemented")
            }
        })


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
}