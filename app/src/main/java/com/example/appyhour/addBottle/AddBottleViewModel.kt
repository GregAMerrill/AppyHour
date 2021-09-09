package com.example.appyhour.addBottle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.appyhour.bottleDatabase.BarDatabaseDao
import com.example.appyhour.bottleDatabase.Bottle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddBottleViewModel(val database: BarDatabaseDao): ViewModel() {

    private suspend fun insert(bottle: Bottle) {
        withContext(Dispatchers.IO) {
            database.insert(bottle)
        }
    }

    fun onSubmitBottle(bottleName: String, bottleType: String) {
        viewModelScope.launch {
            val bottle = Bottle()
            bottle.bottleName = bottleName
            bottle.bottleType = bottleType
            insert(bottle)
        }
    }
    
    class Factory(
        private val dataSource: BarDatabaseDao) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddBottleViewModel::class.java)) {
                return AddBottleViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}