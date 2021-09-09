package com.example.appyhour.recipes.recipeList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.example.appyhour.recipes.ListType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(private val database: RecipesDatabase) {

    val recipes = Transformations.map(database.recipeDao.getRecipes()) {
            it.asDomainModel() }

    fun updateRecipe(recipe: Recipe){
        database.recipeDao.updateRecipe(recipe.asDatabaseRecipe())
    }

    fun getRecipe(id: Long): Recipe{
        return database.recipeDao.getRecipe(id).asDomainModel()
    }

    suspend fun refreshRecipes() {
        withContext(Dispatchers.IO) {
            val networkRecipes = RecipeApi.retrofitService.getRecipes()
            database.recipeDao.insertAll(*networkRecipes.asDatabaseModel())
        }
    }
}