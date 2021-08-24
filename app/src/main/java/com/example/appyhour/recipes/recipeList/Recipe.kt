package com.example.appyhour.recipes.recipeList

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Recipe(
        val id: Long,
        val name: String,
        val ingredients: List<String>,
        val directions: String
)