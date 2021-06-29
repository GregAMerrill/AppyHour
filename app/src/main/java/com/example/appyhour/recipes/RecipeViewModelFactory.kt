package com.example.appyhour.recipes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appyhour.database.BarDatabaseDao

class RecipeViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}