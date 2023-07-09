package com.messenger.cnc.presentation.friendsScreen.findfriendsscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.messenger.cnc.R
import com.messenger.cnc.data.models.User
import com.messenger.cnc.databinding.FragmentFindFriendsScreenBinding
import com.messenger.cnc.domain.ErrorState
import com.messenger.cnc.domain.PendingState
import com.messenger.cnc.domain.SuccessState
import com.messenger.cnc.domain.base.BaseFragment
import com.messenger.cnc.domain.state.Action

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
            // setup user data card
            binding.progressbar.visibility = View.GONE
            binding.userDataCard.visibility = View.VISIBLE
            Glide.with(requireContext())
                .load(user.image)
                .placeholder(R.drawable.ic_user_placeholder)
                .error(R.drawable.ic_user_placeholder)
                .into(binding.userImage)
            binding.userUsername.text = user.name
            binding.userDescription.text = user.description
            binding.moreButton.setOnClickListener {
                it.tag = user
                showPopup(it)
            }
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner) {state ->
            when(state) {
                is PendingState -> {

                    if (state.data == Action.ADD_FRIENDS) {
                        binding.cardProgressbar.visibility = View.VISIBLE
                        return@observe
                    }

                    // show progressbar and user data card
                    binding.progressbar.visibility = View.VISIBLE
                    binding.userDataCard.visibility = View.GONE
                }
                is SuccessState -> {
                    // hide progressbar
                    binding.progressbar.visibility = View.GONE

                    if (state.action == Action.ADD_FRIENDS) {
                        binding.cardProgressbar.visibility = View.GONE
                        Log.d(
                            "Here",
                            "SuccessState: StateDataTypes.ADD_FRIENDS")
                    }
                }
                is ErrorState -> {
                    // TODO
                    // hide progressbar and user data card and show error toast
                    binding.userDataCard.visibility = View.GONE
                    binding.progressbar.visibility = View.GONE
                    binding.cardProgressbar.visibility = View.GONE
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
    }// end onViewCreated

    private fun showPopup(view: View){
        val context = view.context
        val popupMenu = PopupMenu(view.context, view)
        val user = view.tag as User

        popupMenu.menu.add(0,
                           POPUPMENU_CHAT, Menu.NONE, context.getString(R.string.popup_menu_chat))
        popupMenu.menu.add(0,
                           ADD_TO_FRIENDS, Menu.NONE, getString(R.string.add_to_friends))

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                POPUPMENU_CHAT -> {
                    // TODO start chat
                    Toast.makeText(context, "Chat", Toast.LENGTH_SHORT).show()
                }
                ADD_TO_FRIENDS -> {
                    // TODO save to friend
                    viewModel.addFriend(user.id)
                    //findNavController().popBackStack()
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }// end showPopup

    private fun setupNavigation() {
        binding.closeWindowButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    companion object {
        private const val POPUPMENU_CHAT = 0
        private const val ADD_TO_FRIENDS = 1
    }
}