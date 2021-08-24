package com.example.appyhour.home


import android.app.Application
import androidx.lifecycle.*
import com.example.appyhour.bottleDatabase.BarDatabaseDao
import com.example.appyhour.bottleDatabase.Bottle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    val database: BarDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private val _navigateToAddBottle = MutableLiveData<Boolean?>()
    private val _navigateToRecipes = MutableLiveData<Boolean?>()
    private val _navigateToBottle = MutableLiveData<Bottle?>()

    val navigateToAddBottle: LiveData<Boolean?>
    get() = _navigateToAddBottle
    val navigateToRecipes: LiveData<Boolean?>
    get() = _navigateToRecipes
    val navigateToBottle: LiveData<Boolean?>
    get() = _navigateToAddBottle

    val bottles = database.getBottles()

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(bottle: Bottle) {
        withContext(Dispatchers.IO) {
            database.update(bottle)
        }
    }


    fun addNewBottle() {
        _navigateToAddBottle.value = true
    }

    fun viewRecipes() {
        _navigateToRecipes.value = true
    }

    fun doneNavigatingToAddBottle(){
        _navigateToAddBottle.value = null
    }

    fun doneNavigatingToRecipes(){
        _navigateToRecipes.value = null
    }


    fun onBottleClicked(id: Long) {
        viewModelScope.launch {
            val bottleClicked = database.get(id)
            _navigateToBottle.value = bottleClicked
        }
    }

        class Factory(
            private val dataSource: BarDatabaseDao,
            private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}