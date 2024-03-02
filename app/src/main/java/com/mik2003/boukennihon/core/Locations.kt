package com.mik2003.boukennihon.core

import kotlin.math.*

class Coordinate(val latitude: Double, val longitude: Double) {
    val lat: Double
        get() = latitude

    val lon: Double
        get() = longitude

    val radlat: Double
        get() = Math.toRadians(latitude)

    val radlon: Double
        get() = Math.toRadians(longitude)

    fun calculateDistance(coord1: Coordinate, coord2: Coordinate): Double {
        val lat1 = coord1.radlat
        val lon1 = coord1.radlon
        val lat2 = coord2.radlat
        val lon2 = coord2.radlon

        val dlon = lon2 - lon1
        val dlat = lat2 - lat1
        val a = sin(dlat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dlon / 2).pow(2)
        val c = 2 * asin(sqrt(a))
        val r = 6371
        return c * r
    }
}

class Location(val name: String, val coordinates: Coordinate) {
    fun distance(other: Any): Double {
        if (other is Coordinate) {
            return coordinates.calculateDistance(coordinates, other)
        }
        if (other is Location) {
            return coordinates.calculateDistance(coordinates, other.coordinates)
        }
        throw IllegalArgumentException("Unsupported type for distance calculation")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Location) return false

        if (name != other.name) return false
        if (coordinates != other.coordinates) return false

        return true
    }
}

class LocationsList : ArrayList<Location>() {
    fun distancesFromCurrentLocation(currentCoordinate: Coordinate): Map<String, Double> {
        val distancesMap = mutableMapOf<String, Double>()
        for (location in this) {
            distancesMap[location.name] = location.distance(currentCoordinate)
        }
        return distancesMap
    }
}


data class LocationWithDistance(val location: Location, val distance: String)
