package com.danicktakam.demo3andm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.danicktakam.demo3andm.R
import com.danicktakam.demo3andm.db.entity.User
import com.danicktakam.demo3andm.recyclerextentions.onClick
import kotlinx.android.synthetic.main.item_user.view.*

class UserListAdapter : RecyclerView.Adapter<UserViewHolder>() {

    private var arrayUser = mutableListOf<User>()
    var onItemDetailClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent,
                false)).onClick { view, position, type ->
            view.setOnClickListener {
                onItemDetailClick?.invoke(position)
            }
        }
    }

    override fun getItemCount(): Int = arrayUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(arrayUser[position])
    }

    fun setUserList(arrayUser: MutableList<User>) {
        this.arrayUser = arrayUser
        notifyDataSetChanged()
    }
}