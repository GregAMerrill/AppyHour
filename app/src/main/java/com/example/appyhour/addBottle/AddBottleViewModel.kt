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

    private val _navigateToHome = MutableLiveData<Boolean>()

    val navigateToHome : LiveData<Boolean>
    get() = _navigateToHome

    fun doneNavigating() {
        _navigateToHome.value = false
    }

    private suspend fun insert(bottle: Bottle) {
        withContext(Dispatchers.IO) {
            database.insert(bottle)
        }
    }

    fun onSubmitBottle() {
        viewModelScope.launch {
            // IO is a thread pool for running operations that access the disk, such as
            // our Room database.
            val bottle = Bottle()
           //TODO: make fragment accept bottle args
            //tonight.bottleName = name
            //tonight.bottleType = type
            insert(bottle)

            // Setting this state variable to true will alert the observer and trigger navigation.
            _navigateToHome.value = true
        }
    }

}