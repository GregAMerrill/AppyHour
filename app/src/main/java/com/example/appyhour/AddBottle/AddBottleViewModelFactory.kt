package com.example.appyhour.AddBottle

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appyhour.database.BarDatabaseDao

class AddBottleViewModelFactory(
    private val bottleKey: Long,
    private val dataSource: BarDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddBottleViewModel::class.java)) {
            return AddBottleViewModel(bottleKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}