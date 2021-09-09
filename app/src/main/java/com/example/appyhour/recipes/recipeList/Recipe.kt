package com.example.appyhour.recipes.recipeList

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
        val id: Long,
        val name: String,
        val ingredients: List<String>,
        val directions: String,
        var isSaved: Boolean) : Parcelable

fun Recipe.asDatabaseRecipe(): DatabaseRecipe {
    return DatabaseRecipe(
                id = this.id,
                name = this.name,
                ingredients = this.ingredients.toString().trim('[',']'),
                directions = this.directions,
                isSaved = this.isSaved)
}