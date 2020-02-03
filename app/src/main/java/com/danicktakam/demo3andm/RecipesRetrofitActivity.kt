package com.danicktakam.demo3andm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.danicktakam.demo3andm.adapter.RecipeAdapter
import com.danicktakam.demo3andm.db.entity.Recipe
import com.danicktakam.demo3andm.services.IRecipeService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recipes_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipesRetrofitActivity : AppCompatActivity() {

    private  lateinit var recipeAdapter: RecipeAdapter
    private var recipes: MutableList<Recipe> = ArrayList<Recipe>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes_retrofit)
    }

    fun initAdapter(){
        this.recipeAdapter = RecipeAdapter(this)
        this.recipeAdapter.onItemDetailClick = {
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("recipe", Gson().toJson(recipes[it]))
            startActivity(intent)
            Log.i("info :", "finish")
        }

        rcyclv_recipes.layoutManager = LinearLayoutManager(this)
    }
    fun loadingServerData(){
        initAdapter()

        //http://10.0.2.2/
        //"http://demo.net/"
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2/demo/")
            .addConverterFactory (GsonConverterFactory.create())
            .build()

        val service = retrofit.create(IRecipeService::class.java)

        service.getAllRecipes().enqueue(object: Callback<List<Recipe>> {
            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                Log.e("error :",t.message)
            }

            override fun onResponse(
                call: Call<List<Recipe>>,
                response: Response<List<Recipe>>
            ) {
                recipes.addAll(response.body()!!.toList())
                /*
                    response.body()!!.forEach{
                        Log.i("Name ",it.Name)
                        students.add(it)
                    }
                */


                recipeAdapter.setRecipeList(recipes)
                rcyclv_recipes.adapter = recipeAdapter
            }

        })
    }
}
