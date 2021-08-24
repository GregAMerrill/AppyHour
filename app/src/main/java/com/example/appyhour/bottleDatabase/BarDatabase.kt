package com.example.appyhour.bottleDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bottle::class], version = 1, exportSchema = false)
abstract class BarDatabase : RoomDatabase() {

    abstract val barDatabaseDao: BarDatabaseDao
    companion object {

        @Volatile
        private var INSTANCE: BarDatabase? = null

        fun getInstance(context: Context): BarDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BarDatabase::class.java,
                        "bar_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

