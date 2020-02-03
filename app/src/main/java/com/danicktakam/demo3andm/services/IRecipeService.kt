package com.danicktakam.demo3andm.services

import com.danicktakam.demo3andm.db.entity.Recipe
import retrofit2.Call
import retrofit2.http.GET

interface IRecipeService {
    @GET("recipes.json")
    fun getAllRecipes(): Call<List<Recipe>>
}