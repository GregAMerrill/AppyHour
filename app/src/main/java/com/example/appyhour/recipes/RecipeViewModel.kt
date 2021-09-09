package com.example.appyhour.recipes

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.appyhour.addBottle.AddBottleViewModel
import com.example.appyhour.bottleDatabase.BarDatabaseDao
import com.example.appyhour.recipes.recipeList.Recipe
import com.example.appyhour.recipes.recipeList.RecipeRepository
import com.example.appyhour.recipes.recipeList.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

enum class ListType { SAVED, FULL }

class RecipeViewModel(application: Application, private val state: SavedStateHandle): ViewModel() {

    private val database = getDatabase(application)
    private val recipesRepository = RecipeRepository(database)

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navToRecipeDetail = MutableLiveData<Recipe?>()
    val navToRecipeDetail: LiveData<Recipe?>
        get() = _navToRecipeDetail

    private val _recipeFilter = MutableLiveData<ListType>()
    val recipeFilter:LiveData<ListType>
    get() = _recipeFilter

    val recipeList = recipesRepository.recipes

    init {
        _recipeFilter.value = state.get("recipeFilter") ?: ListType.SAVED
        fetchRecipes()
    }

    fun changeFilter() {
        fetchRecipes()
        _recipeFilter.value = if(recipeFilter.value == ListType.SAVED) ListType.FULL else ListType.SAVED
    }

    private fun fetchRecipes() = viewModelScope.launch {
        recipesRepository.refreshRecipes()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun saveState() {
        state.set("recipeFilter", _recipeFilter.value)
    }

    fun navigateToRecipeDetail(recipe: Recipe) {
        _navToRecipeDetail.value = recipe
    }

    fun doneNavigatingToDetail() {
        _navToRecipeDetail.value = null
    }

    class Factory(
        private val application: Application,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null) :
        AbstractSavedStateViewModelFactory(owner, defaultArgs) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return RecipeViewModel(application, handle) as T
        }
    }
}