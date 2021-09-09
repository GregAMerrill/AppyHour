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
    val directions: String,
    var isSaved: Boolean
)

fun DatabaseRecipe.asDomainModel(): Recipe {
    return Recipe(
        id = this.id,
        name = this.name,
        ingredients = this.ingredients.split(", "),
        directions = this.directions,
        isSaved = this.isSaved
    )
}

fun List<DatabaseRecipe>.asDomainModel(): List<Recipe> {
    return map {
        Recipe(
            id = it.id,
            name = it.name,
            ingredients = it.ingredients.split(", "),
            directions = it.directions,
            isSaved = it.isSaved
        )
    }
}
