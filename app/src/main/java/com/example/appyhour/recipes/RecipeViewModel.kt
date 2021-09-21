package com.example.appyhour.recipes

import android.app.Application
import android.os.Bundle
import android.widget.Toast
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

    private val _filteredList = MutableLiveData<List<Recipe>>()
    val filteredList: LiveData<List<Recipe>>
    get() = _filteredList

    private val _recipeFilter = MutableLiveData<ListType>()
    val recipeFilter:LiveData<ListType>
    get() = _recipeFilter

    val recipeList = recipesRepository.recipes

    init {
        _recipeFilter.value = state.get("recipeFilter") ?: ListType.SAVED
        fetchRecipes().invokeOnCompletion { _filteredList.value = if(_recipeFilter.value == ListType.SAVED) filterSavedRecipes() else recipeList.value }
    }

    fun changeFilter() {
        if (_recipeFilter.value == ListType.SAVED) {
            fetchRecipes()
            _filteredList.value = recipeList.value?.sortedBy {it.name}
            _recipeFilter.value = ListType.FULL
        } else {
            _filteredList.value = filterSavedRecipes()
            _recipeFilter.value = ListType.SAVED
        }
    }

    private fun filterSavedRecipes(): List<Recipe> {
        if(recipeList.value != null) {
            return recipeList.value!!.filter { recipe -> recipe.isSaved }.sortedBy { it.name }
        }
        else return emptyList()
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