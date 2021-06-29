package com.example.appyhour.home


import android.app.Application
import androidx.lifecycle.*
import com.example.appyhour.database.BarDatabaseDao
import com.example.appyhour.database.Bottle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    val database: BarDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private val _navigateToAddBottle = MutableLiveData<Bottle>()
    private val _navigateToRecipes = MutableLiveData<Boolean>()

    val navigateToAddBottle: LiveData<Bottle>
    get() = _navigateToAddBottle
    val navigateToRecipes: LiveData<Boolean>
    get() = _navigateToRecipes

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

    private suspend fun insert(bottle: Bottle) {
        withContext(Dispatchers.IO) {
            database.insert(bottle)
        }
    }

    fun addNewBottle() {
        viewModelScope.launch {
            val newBottle = Bottle()
            insert(newBottle)
            _navigateToAddBottle.value = newBottle
        }
    }

    fun viewRecipes() {
        _navigateToRecipes.value = true
    }

    fun doneNavigatingToAddBottle(){
        _navigateToAddBottle.value = null
    }

    fun onBottleClicked(id: Long) {
        viewModelScope.launch {
            val bottleClicked = database.get(id)
        }
    }

}