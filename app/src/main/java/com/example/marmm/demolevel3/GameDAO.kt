package com.example.marmm.demolevel3

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface GameDAO {
    @get:Query("SELECT * FROM game")
    val allGames: MutableList<Game>

    @Insert
    fun insertGames(game: Game)

    @Delete
    fun deleteGames(game: Game)

    @Update
    fun updateGames(game: Game)

}


