package com.example.appyhour.recipes.recipeList

import kotlinx.coroutines.*

class RecipeRepository(private val database: RecipesDatabase) {

    private val repoScope = CoroutineScope(Dispatchers.IO)

    fun updateRecipe(recipe: Recipe){
        database.recipeDao.updateRecipe(recipe.asDatabaseRecipe())
    }

    suspend fun getRecipe(id: Long): Recipe = withContext(Dispatchers.IO) {
         return@withContext database.recipeDao.getRecipe(id).asDomainModel()
    }

    suspend fun getAllRecipes(): List<Recipe> {
        return database.recipeDao.getRecipes().map {
            it.asDomainModel()
        }
    }


    suspend fun refreshRecipes() {
        withContext(Dispatchers.IO) {
            val networkRecipes = RecipeApi.retrofitService.getRecipes()
            database.recipeDao.insertAll(*networkRecipes.asDatabaseModel())
        }
    }

    fun cancelJob() {
        repoScope.cancel()
    }
}