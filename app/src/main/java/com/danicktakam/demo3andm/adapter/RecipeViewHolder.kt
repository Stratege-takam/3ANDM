package com.danicktakam.demo3andm.adapter


import android.R
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.danicktakam.demo3andm.db.entity.Recipe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recipe.view.*
import java.io.InputStream
import java.net.URL


class RecipeViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(recipe: Recipe) {
        Log.i("recipe viewholder : ",recipe.imageURL)
        itemView.tv_item_recipe_name.text = recipe.name
        itemView.tv_item_recipe_description.text =  recipe.getDescription()
       /* val newurl = URL(recipe.imageURL)
        val mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream())
        itemView.imgv_item_recipe_img.setImageBitmap(mIcon_val)*/


        //Glide.with(context).load(recipe.imageURL).into(imageView);


        Picasso.get()
            .load(recipe.imageURL)
            .resize(50, 50)
            .centerCrop()
            .into(itemView.imgv_item_recipe_img)

        /*Picasso.with(context).load(recipe.imageURL).resize(50, 50).
            centerCrop().into(itemView.imgv_item_recipe_img)*/

       /* val drawable =
            LoadImageFromWebOperations(recipe.imageURL)
        itemView.imgv_item_recipe_img.setImageDrawable(drawable)*/
    }


    private fun LoadImageFromWebOperations(url: String): Drawable? {
        return try {
            val input: InputStream = URL(url).getContent() as InputStream
            Drawable.createFromStream(input, "src name")
        } catch (e: Exception) {
            println("Exc=$e")
            null
        }
    }
}