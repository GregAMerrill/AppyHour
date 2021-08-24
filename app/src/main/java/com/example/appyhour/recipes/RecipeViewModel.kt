package com.example.appyhour.recipes

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.appyhour.recipes.recipeList.Recipe
import com.example.appyhour.recipes.recipeList.RecipeApi
import com.example.appyhour.recipes.recipeList.RecipeRepository
import com.example.appyhour.recipes.recipeList.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

enum class RecipeApiStatus { LOADING, ERROR, DONE }

class RecipeViewModel(application: Application): ViewModel() {

    private val database = getDatabase(application)
    private val recipesRepository = RecipeRepository(database)

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        viewModelScope.launch {
            recipesRepository.refreshRecipes()
        }
    }

    val recipeList = recipesRepository.recipes

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
                return RecipeViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}