package com.danicktakam.demo3andm.db.entity

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("name")
    var name: String,
    @SerializedName("ingredients")
    var ingredients: ArrayList<Ingredient>,
    @SerializedName("steps")
    var steps: ArrayList<String>,
    @SerializedName("timers")
    var timers: ArrayList<Int>,
    @SerializedName("imageURL")
    var imageURL: String,
    @SerializedName("originalURL")
    var originalURL: String
) {

    fun getDescription() = steps?.get(0)
}