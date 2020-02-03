package com.danicktakam.demo3andm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danicktakam.demo3andm.db.Dao.UserDAO
import com.danicktakam.demo3andm.db.entity.User

@Database(entities = [(User::class)], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {

    //Generate Singleton Instance
    companion object {
        private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context,
                    ApplicationDatabase::class.java, "demo3andm.db").allowMainThreadQueries().build()
            }
            return INSTANCE!!
        }

    }
    abstract fun userDao(): UserDAO
}