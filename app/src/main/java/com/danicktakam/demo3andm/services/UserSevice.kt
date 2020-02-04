package com.danicktakam.demo3andm.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserSevice{

    fun getIfInternetIsAvailable(context: Context): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    fun getRetrofitService() =  Retrofit.Builder()
            .baseUrl("https://10.0.2.2:5001")
            .addConverterFactory (GsonConverterFactory.create())
            .build()


}