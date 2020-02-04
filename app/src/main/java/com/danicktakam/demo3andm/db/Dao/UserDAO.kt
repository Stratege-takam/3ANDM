package com.danicktakam.demo3andm.db.Dao

import androidx.room.*
import com.danicktakam.demo3andm.db.entity.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    fun getAll(): MutableList<User>

    @Query("SELECT * FROM users WHERE Id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM users WHERE Firstname LIKE :firstname Or " + "Lastname LIKE :lastname LIMIT 1")
    fun findByFirstnameOrLastName(firstname: String, lastname: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Update
    fun updateAll(vararg users: User)

    @Delete
    fun delete(user: User)
}