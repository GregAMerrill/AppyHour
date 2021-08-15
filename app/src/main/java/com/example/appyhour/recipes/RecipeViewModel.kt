package com.example.appyhour.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appyhour.recipes.recipeList.Recipe
import com.example.appyhour.recipes.recipeList.RecipeApi
import kotlinx.coroutines.launch

enum class RecipeApiStatus { LOADING, ERROR, DONE }

class RecipeViewModel: ViewModel() {

    private val _status = MutableLiveData<RecipeApiStatus>()
    val status: LiveData<RecipeApiStatus>
        get() = _status

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>>
        get() = _recipes

    private val _navigateToSelectedRecipe = MutableLiveData<Recipe?>()
    val navigateToSelectedRecipe: LiveData<Recipe?>
        get() = _navigateToSelectedRecipe

    init {
        getRecipes()
    }

    private fun getRecipes() {
        viewModelScope.launch {
            _status.value = RecipeApiStatus.LOADING
            try {
                _recipes.value = RecipeApi.retrofitService.getRecipes()
                _status.value = RecipeApiStatus.DONE
            } catch (e: Exception) {
                _status.value = RecipeApiStatus.ERROR
                _recipes.value = ArrayList()
            }
        }
    }
    fun displayRecipeDetails(recipe: Recipe) {
        _navigateToSelectedRecipe.value = recipe
    }

    fun displayRecipeDetailsComplete() {
        _navigateToSelectedRecipe.value = null
    }
}