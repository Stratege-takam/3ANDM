package com.danicktakam.demo3andm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_recipe.*

class RecipeActivity : AppCompatActivity() {

    private var recipes: MutableList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        this.recipes = resources
            .getStringArray(R.array.recipes_list)
            .asList() as MutableList<String>
       // Log.i("My recipes", recipes.toString())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recipes)
        lstv_recipe_list.adapter = adapter
    }


}
