package com.example.cnc.presentation.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cnc.R
import com.example.cnc.databinding.FragmentChatsBinding

class ChatsFragment: Fragment(R.layout.fragment_chats) {
    private lateinit var binding: FragmentChatsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChatsBinding.inflate(inflater)

        return binding.root
    }// end onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeWindowButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }// end onViewCreated
}