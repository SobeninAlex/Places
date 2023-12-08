package com.example.places.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.places.data.PlaceDao
import com.example.places.data.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val placeDao: PlaceDao
): ViewModel() {

    val places: LiveData<List<Place>> = placeDao.getAllPlaces().asLiveData()

    fun getPlaceById(id: Long): LiveData<Place> = placeDao.getPlaceById(id).asLiveData()

    fun addPlace(name: String, address: String, notes: String) {
        val place = Place(
            name = name,
            address = address,
            notes = notes
        )
        viewModelScope.launch {
            placeDao.insert(place)
        }
    }

    fun updatePlace(id: Long, name: String, address: String, notes: String) {
        val place = Place(
            id = id,
            name = name,
            address = address,
            notes = notes
        )
        viewModelScope.launch(Dispatchers.IO) {
            placeDao.update(place)
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            placeDao.delete(place)
        }
    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }

}


