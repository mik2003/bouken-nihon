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
        val locationsWithDistances = locationsList.mapNotNull { location ->
            distancesMap[location.name]?.let { distance ->
                Pair(location, distance)
            }
        }.sortedBy { it.second }
            .map { (location, distance) ->
                val formattedDistance = "%.1f km".format(distance)
                LocationWithDistance(location, formattedDistance)
            }
        _locationsWithDistance.value = locationsWithDistances
    }
}

