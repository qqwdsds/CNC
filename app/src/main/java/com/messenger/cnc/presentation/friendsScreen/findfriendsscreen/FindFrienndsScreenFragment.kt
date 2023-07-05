package com.messenger.cnc.presentation.friendsScreen.findfriendsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FragmentFindFriendsScreenBinding
import com.messenger.cnc.domain.base.BaseFragment

class FindFrienndsScreenFragment: BaseFragment() {
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")

    private lateinit var binding: FragmentFindFriendsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
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
    }

    private fun setupNavigation() {
        binding.closeWindowButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}