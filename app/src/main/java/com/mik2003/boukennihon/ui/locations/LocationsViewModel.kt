package com.mik2003.boukennihon.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mik2003.boukennihon.core.*

class LocationsViewModel : ViewModel() {
    private val _locationsWithDistance = MutableLiveData<List<LocationWithDistance>>().apply {
        value = emptyList() // Set an initial empty list if needed
    }
    val locationsWithDistance: LiveData<List<LocationWithDistance>> = _locationsWithDistance

    fun setLocationsWithDistances(locationsList: LocationsList, distancesMap: Map<String, Double>) {
        val locationsWithDistances = locationsList.map { location ->
            val distance = String.format("%.1f", distancesMap[location.name]) + " km"
            LocationWithDistance(location, distance)
        }
        _locationsWithDistance.value = locationsWithDistances
    }
}

