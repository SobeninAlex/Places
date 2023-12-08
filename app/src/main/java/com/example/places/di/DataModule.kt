package com.example.places.di

import android.content.Context
import com.example.places.data.PlaceDatabase
import com.example.places.data.PlaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providePlaceDao(@ApplicationContext context: Context): PlaceDao {
        return PlaceDatabase.getDatabase(context).placeDao()
    }

}