package com.messenger.cnc.presentation.friendsScreen

import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.messenger.cnc.R
import com.messenger.cnc.databinding.FriendsItemBinding
import com.messenger.cnc.data.models.User

class FriendsAdapter: ListAdapter<User, FriendsAdapter.FriendsHolder>(FriendsDiffCallback()), View.OnClickListener {
    private var defaultUserList = ArrayList<User>()
    private var filter: ValueFilter? = null

    fun setUser(user: User) {
        Log.d("User", "User: $user")
            val userIndex = defaultUserList.indexOf(user)
            if (userIndex == -1) {
                defaultUserList.add(user)
                this.submitList(defaultUserList)
            }
    }

    fun removeUser(user: User) {
        val userIndex = defaultUserList.indexOf(user)
        if (userIndex != -1) {
            defaultUserList.removeAt(userIndex)
            this.submitList(defaultUserList)
        }
    }

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

    fun getFilter(): ValueFilter {
        if (filter == null) {
            filter = ValueFilter()
        }
        return filter!!
    }

    companion object {
        private const val POPUPMENU_CHAT = 0
        private const val POPUPMENU_DELETE = 1
        private const val POPUPMENU_SHOW_INFO = 2
    }// end companion object


    class FriendsHolder(val binding: FriendsItemBinding):RecyclerView.ViewHolder(binding.root)
    inner class ValueFilter: Filter() {
        override fun performFiltering(text: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (!text.isNullOrEmpty()) {
                val filterList = ArrayList<User>()
                for (item in defaultUserList) {
                    if (item.name.lowercase().contains(text.toString().lowercase())) {
                        filterList.add(item)
                    }
                }
                filterResults.count = filterList.size
                filterResults.values = filterList
            } else {
                filterResults.count = defaultUserList.size
                filterResults.values = defaultUserList
            }
            return filterResults
        }

        override fun publishResults(
            p0: CharSequence?,
            filterResult: FilterResults) {
            this@FriendsAdapter.submitList(filterResult.values as List<User>)
            notifyDataSetChanged()
        }

    }
}