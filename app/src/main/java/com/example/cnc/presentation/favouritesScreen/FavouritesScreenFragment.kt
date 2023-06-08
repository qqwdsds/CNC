package com.example.cnc.presentation.favouritesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cnc.R
import com.example.cnc.databinding.FragmentFavouriteSceenBinding

class FavouritesScreenFragment : Fragment(R.layout.fragment_favourite_sceen) {
    private lateinit var binding: FragmentFavouriteSceenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavouriteSceenBinding.inflate(inflater)

        return binding.root
    }// end onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeWindowButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }// end onViewCreated
}