package com.danicktakam.demo3andm.db.entity

import com.google.gson.annotations.SerializedName

data class Ingredient constructor(
     @SerializedName("quantity")
     var quantity: String,
     @SerializedName("name")
     var name: String,
     @SerializedName("type")
     var type: String) {
}