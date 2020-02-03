package com.danicktakam.demo3andm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danicktakam.demo3andm.R
import com.danicktakam.demo3andm.db.entity.Recipe
import com.danicktakam.demo3andm.db.entity.User
import com.danicktakam.demo3andm.recyclerextentions.onClick

class RecipeAdapter(private val context: Context) : RecyclerView.Adapter<RecipeViewHolder>() {

    private var arrayRecipe = mutableListOf<Recipe>()
    var onItemDetailClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user, parent,
                false), context).onClick { view, position, type ->
            view.setOnClickListener {
                onItemDetailClick?.invoke(position)
            }
        }
    }

    override fun getItemCount(): Int = arrayRecipe.size



    fun setRecipeList(arrayRecipe: MutableList<Recipe>) {
        this.arrayRecipe = arrayRecipe
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bindData(arrayRecipe[position])
    }
}