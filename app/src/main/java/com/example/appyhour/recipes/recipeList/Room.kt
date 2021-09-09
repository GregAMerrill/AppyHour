package com.example.appyhour.recipes.recipeList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {
    @Query("select * from databaserecipe")
    fun getRecipes(): LiveData<List<DatabaseRecipe>>

    @Query("select * from databaserecipe where id=:id")
    fun getRecipe(id: Long): DatabaseRecipe

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecipe(recipe: DatabaseRecipe)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg recipes: DatabaseRecipe)
}

@Database(entities = [DatabaseRecipe::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
}

private lateinit var INSTANCE: RecipesDatabase

fun getDatabase(context: Context): RecipesDatabase {
    synchronized(RecipesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                RecipesDatabase::class.java,
                "recipes").build()
        }
    }
    return INSTANCE
}
