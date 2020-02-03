package com.danicktakam.demo3andm.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User(
    //For autoincrement primary key
    @PrimaryKey(autoGenerate = true)
    var Id: Int = 0,

    @ColumnInfo(name = "Firstname")
    var Firstname: String? = null,

    @ColumnInfo(name = "Lastname")
    var Lastname: String? = null,

    @ColumnInfo(name = "Gender")
    var Gender: String? = null,

    @ColumnInfo(name = "Country")
    var Country: String? = null
) {
    constructor() : this(0, "", "")
}