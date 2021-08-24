package com.example.appyhour.recipes.recipeList

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
data class DatabaseRecipe(
    @PrimaryKey
    val id: Long,
    val name: String,
    val ingredients: String,
    val directions: String
)

fun List<DatabaseRecipe>.asDomainModel(): List<Recipe> {
    return map {
        Recipe(
            id = it.id,
            name = it.name,
            ingredients = it.ingredients.split(", "),
            directions = it.directions
        )
    }
}
