package com.example.appyhour.bottleDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bar_table")
data class Bottle(
    @PrimaryKey(autoGenerate = true)
    var bottleId: Long = 0L,

    @ColumnInfo(name = "bottle_name")
    var bottleName: String = "Vodka",

    @ColumnInfo(name = "bottle_type")
    var bottleType: String = "Vodka")