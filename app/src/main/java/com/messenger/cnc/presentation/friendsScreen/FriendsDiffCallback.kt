package com.messenger.cnc.presentation.friendsScreen

import androidx.recyclerview.widget.DiffUtil
import com.messenger.cnc.data.models.User

class FriendsDiffCallback: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}