package com.danicktakam.demo3andm.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.danicktakam.demo3andm.db.entity.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(user: User) {
        itemView.tv_item_user_name.text = "${user.Firstname}  ${user.Lastname}"
        itemView.tv_item_user_country.text =  user.Country
    }
}