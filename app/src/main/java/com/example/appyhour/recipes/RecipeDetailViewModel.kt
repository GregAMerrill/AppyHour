package com.example.appyhour.recipes

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appyhour.addBottle.AddBottleViewModel
import com.example.appyhour.bottleDatabase.BarDatabaseDao
import com.example.appyhour.recipes.recipeList.Recipe
import com.example.appyhour.recipes.recipeList.RecipeRepository
import com.example.appyhour.recipes.recipeList.asDatabaseRecipe
import com.example.appyhour.recipes.recipeList.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RecipeDetailViewModel(application: Application) : ViewModel() {
    private val database = getDatabase(application)
    private val recipesRepository = RecipeRepository(database)
    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    private val _recipeFavorited = MutableLiveData<Boolean>()
    val recipeFavorited: LiveData<Boolean>
    get() = _recipeFavorited

    fun saveRecipe(recipe: Recipe) {
        recipe.isSaved = !recipe.isSaved
        _recipeFavorited.value = recipe.isSaved
        viewModelScope.launch{
            recipesRepository.updateRecipe(recipe)
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeDetailViewModel::class.java)) {
                return RecipeDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
