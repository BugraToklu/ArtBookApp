package com.example.artbooktesting.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.*

@Dao
interface ArtDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
      fun insertArt(art: Art)

    @Delete
     fun deleteArt(art: Art)

    @Query("SELECT * FROM arts")
    fun observeArt() : LiveData<List<Art>>
}