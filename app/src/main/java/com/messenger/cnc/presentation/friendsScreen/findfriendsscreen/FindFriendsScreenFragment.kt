package com.messenger.cnc.presentation.friendsScreen.findfriendsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentFindFriendsScreenBinding
import com.messenger.cnc.domain.ErrorState
import com.messenger.cnc.domain.PendingState
import com.messenger.cnc.domain.SuccessState
import com.messenger.cnc.domain.base.BaseFragment

class FindFriendsScreenFragment: BaseFragment() {
    override val viewModel by viewModels<FindFriendsViewModel>()

    private lateinit var binding: FragmentFindFriendsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentFindFriendsScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?) {
        super.onViewCreated(
            view,
            savedInstanceState)
        setupNavigation()

        viewModel.searchLiveData.observe(viewLifecycleOwner) { user ->
            // TODO auth.currentUser == user.id
            binding.progressbar.visibility = View.GONE
            binding.userDataCard.visibility = View.VISIBLE
            Glide.with(requireContext())
                .load(user.image)
                .placeholder(R.drawable.ic_user_placeholder)
                .error(R.drawable.ic_user_placeholder)
                .into(binding.userImage)
            binding.userUsername.text = user.name
            binding.userDescription.text = user.description
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner) {state ->
            when(state) {
                is PendingState -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.userDataCard.visibility = View.GONE
                }
                is SuccessState -> {
                    binding.progressbar.visibility = View.GONE
                }
                is ErrorState -> {
                    // TODO
                    binding.userDataCard.visibility = View.GONE
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        state.error.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.searchFriend.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                if (!text.isNullOrBlank()) {
                    viewModel.searchUser(text.trim())
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setupNavigation() {
        binding.closeWindowButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}