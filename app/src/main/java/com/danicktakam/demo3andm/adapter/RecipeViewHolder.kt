package com.danicktakam.demo3andm.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.danicktakam.demo3andm.db.entity.Recipe
import com.danicktakam.demo3andm.db.entity.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recipe.view.*
import kotlinx.android.synthetic.main.item_user.view.*

class RecipeViewHolder( itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
    fun bindData(recipe: Recipe) {
        itemView.tv_item_recipe_name.text = recipe.name
        itemView.tv_item_recipe_description.text =  recipe.getDescription()
        Picasso.with(context).load(recipe.imageURL).resize(50, 50).
            centerCrop().into(itemView.imgv_item_recipe_img)
    }
}