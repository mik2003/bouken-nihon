package com.mik2003.boukennihon.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mik2003.boukennihon.core.*

class LocationsViewModel : ViewModel() {

    private val _locations = MutableLiveData<List<Location>>().apply {
        value = emptyList() // Set an initial empty list if needed
    }
    val locations: LiveData<List<Location>> = _locations

    private val _locationsWithDistance = MutableLiveData<List<LocationWithDistance>>().apply {
        value = emptyList() // Set an initial empty list if needed
    }
    val locationsWithDistance: LiveData<List<LocationWithDistance>> = _locationsWithDistance

    fun setLocations(locationsList: LocationsList) {
        _locations.value = locationsList.toList()
    }

    fun setLocationsWithDistances(locationsList: LocationsList, distancesMap: Map<String, Double>) {
        val locationsWithDistances = locationsList.map { location ->
            val distance = distancesMap[location.name] ?: 0.0
            LocationWithDistance(location, distance)
        }
        _locationsWithDistance.value = locationsWithDistances
    }
}

