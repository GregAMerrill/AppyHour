package com.example.appyhour.recipes.recipeList

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
        val id: Long,
        val Name: String,
        val Ingredients: List<String>,
        val Directions: String
): Parcelable {
}