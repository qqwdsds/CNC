package com.example.cnc.presentation.friendsScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cnc.R
import com.example.cnc.databinding.FriendsItemBinding
import com.example.cnc.presentation.models.User

class FriendsAdapter: ListAdapter<User, FriendsAdapter.FriendsHolder>(FriendsDiffCallback()), View.OnClickListener {
    class FriendsHolder(val binding: FriendsItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FriendsItemBinding.inflate(inflater)
        binding.moreButton.setOnClickListener(this)
        binding.root.setOnClickListener(this)
        return FriendsHolder(binding)
    }// end onCreateViewHolder

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        val user = getItem(position)
        holder.binding.username.text = user.name
        holder.binding.userDescription.text = user.description
    }// end onBindViewHolder

    override fun onClick(view: View) {
        showPopup(view)
    }// end onClick

    private fun showPopup(view: View){
        val context = view.context
        val popupMenu = PopupMenu(view.context, view)

        popupMenu.menu.add(0, POPUPMENU_CHAT, Menu.NONE, context.getString(R.string.popup_menu_chat))
        popupMenu.menu.add(0, POPUPMENU_SHOW_INFO, Menu.NONE, context.getString(R.string.popup_menu_info))
        popupMenu.menu.add(0, POPUPMENU_DELETE, Menu.NONE, context.getString(R.string.popup_menu_delete))

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                POPUPMENU_CHAT -> Toast.makeText(context, "Chat", Toast.LENGTH_SHORT).show()
                POPUPMENU_SHOW_INFO -> Toast.makeText(context, "Info", Toast.LENGTH_SHORT).show()
                POPUPMENU_DELETE -> Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show()

            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }// end showPopup

    companion object {
        private const val POPUPMENU_CHAT = 0
        private const val POPUPMENU_DELETE = 1
        private const val POPUPMENU_SHOW_INFO = 2
    }// end companion object
}