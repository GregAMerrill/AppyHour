package com.example.appyhour.recipes.recipeList

import androidx.room.TypeConverters
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkRecipeContainer(val recipes: List<NetworkRecipe>)

@JsonClass(generateAdapter = true)
data class NetworkRecipe(
    val id: Long,
    val name: String,
    val ingredients: String,
    val directions: String)

fun NetworkRecipeContainer.asDomainModel(): List<Recipe> {
    return recipes.map {
        Recipe(
            id = it.id,
            name = it.name,
            ingredients = it.ingredients.split(", "),
            directions = it.directions,
            isSaved = false)
    }
}

fun NetworkRecipeContainer.asDatabaseModel(): Array<DatabaseRecipe> {
    return recipes.map {
        DatabaseRecipe(
            id = it.id,
            name = it.name,
            ingredients = it.ingredients,
            directions = it.directions,
            isSaved = false)
    }.toTypedArray()
}

