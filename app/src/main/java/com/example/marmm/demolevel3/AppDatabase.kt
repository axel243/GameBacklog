package com.example.marmm.demolevel3

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Game::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDAO

    companion object {
        private val NAME_DATABASE = "game_db"
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context, AppDatabase::class.java, NAME_DATABASE).build()

            }

            return sInstance as AppDatabase

        }
    }

}
