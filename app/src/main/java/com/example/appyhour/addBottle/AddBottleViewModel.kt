package com.example.appyhour.addBottle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appyhour.database.BarDatabaseDao
import com.example.appyhour.database.Bottle
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
}