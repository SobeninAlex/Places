package com.example.places.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.places.data.model.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("select * from places_database")
    fun getAllPlaces(): Flow<List<Place>>

    @Query("select * from places_database where id = :id")
    fun getPlaceById(id: Long): Flow<Place>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: Place)

    @Update
    suspend fun update(place: Place)

    @Delete
    suspend fun delete(place: Place)

}
