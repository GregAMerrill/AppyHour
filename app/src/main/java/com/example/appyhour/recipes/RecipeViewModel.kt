package com.example.appyhour.recipes

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
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

    private val _recipeList = MutableLiveData<List<Recipe>>()
    val recipeList: LiveData<List<Recipe>>
    get() = _recipeList

    private val _recipeFilter = MutableLiveData<ListType>()
    val recipeFilter:LiveData<ListType>
    get() = _recipeFilter

    init {
        fetchRecipes()
        _recipeFilter.value = state.get("recipeFilter") ?: ListType.SAVED
        when(_recipeFilter.value) {
            ListType.FULL -> getAllRecipes()
            ListType.SAVED -> _recipeList.value = getSavedRecipes()
        }
    }

    fun changeFilter() {
        if (_recipeFilter.value == ListType.SAVED) {
            fetchRecipes()
            getAllRecipes()
            _recipeFilter.value = ListType.FULL
        } else {
            _recipeList.value = getSavedRecipes()
            _recipeFilter.value = ListType.SAVED
        }
    }

    private fun getSavedRecipes(): List<Recipe> {
        if(_recipeList.value != null) {
            return _recipeList.value!!.filter { recipe -> recipe.isSaved }
        }
        return emptyList()
    }

    private fun fetchRecipes() = viewModelScope.launch {
        recipesRepository.refreshRecipes()
    }

    private fun getAllRecipes() {
        viewModelScope.launch { _recipeList.value = recipesRepository.getAllRecipes() }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        recipesRepository.cancelJob()
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