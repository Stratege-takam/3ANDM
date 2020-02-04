package com.danicktakam.demo3andm.services
import com.danicktakam.demo3andm.db.entity.User
import retrofit2.Call
import retrofit2.http.*


interface IUserService {
    @POST("users/new")
    fun createUser(@Body user: User):  Call<User>

    @PUT("users/{id}")
    fun updateUser(@Part("id") id: Int, @Body user: User):  Call<User>

    @DELETE("users/{id}")
    fun deleteUser(@Part("id") id: Int):  Call<User>

    @GET("users")
    fun getUser(): Call<List<User>>

    @GET("users/{id}")
    fun getUserById(@Part("id") id: Int): Call<User>
}