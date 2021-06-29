package com.example.appyhour.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Liquors(val type: String){
    VODKA("Vodka"),
    WHISKEY("Whiskey"),
    GIN("Gin"),
    RUM("Rum"),
    BRANDY("Brandy"),
    TEQUILA("Tequila")
}

@Entity(tableName = "bar_table")
data class Bottle(
    @PrimaryKey(autoGenerate = true)
    var bottleId: Long = 0L,

    @ColumnInfo(name = "bottle_type")
    val bottleType: Liquors = Liquors.VODKA)