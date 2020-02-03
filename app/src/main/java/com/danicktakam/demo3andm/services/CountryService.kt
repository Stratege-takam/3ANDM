package com.danicktakam.demo3andm.services

import android.content.Context
import android.util.Log
import com.danicktakam.demo3andm.db.entity.Country
import com.google.gson.Gson
import java.io.IOException
import java.lang.Exception
import java.nio.charset.Charset

class CountryService(val context: Context): ICountryService {

    private fun loadJSONFromAssets(): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open("country.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.defaultCharset())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }


    override  fun getAllCountry(): ArrayList<Country> {
        try {
            val gson = Gson()
            val json = loadJSONFromAssets()
            //  Log.i("info", json)
            val model= gson.fromJson(json,Array<Country>::class.java).toList()
           // Log.i("model", model.toString())
            return model as ArrayList<Country>
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e.message)

        }
    }
}