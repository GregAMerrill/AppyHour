package com.example.appyhour.recipes.recipeList


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(private val database: RecipesDatabase) {

    val recipes: LiveData<List<Recipe>> =
        Transformations.map(database.recipeDao.getRecipes()) {
            it.asDomainModel()
        }

    suspend fun refreshRecipes() {
        withContext(Dispatchers.IO) {
            val recipes = RecipeApi.retrofitService.getRecipes()
            database.recipeDao.insertAll(*recipes.asDatabaseModel())
        }
    }
}