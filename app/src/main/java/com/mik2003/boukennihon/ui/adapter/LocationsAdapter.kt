package com.mik2003.boukennihon.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mik2003.boukennihon.core.LocationWithDistance
import com.mik2003.boukennihon.databinding.ItemLocationBinding


class LocationsAdapter : ListAdapter<LocationWithDistance, LocationsAdapter.LocationViewHolder>(LocationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.clear() // Clear previous values
        holder.bind(location) // Bind new data
    }

    class LocationViewHolder(private val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lwd: LocationWithDistance) {
            binding.apply {
                // Bind location data to views
                nameTextView.text = lwd.location.name
                coordinatesTextView.text = "${lwd.location.coordinates.lat}, ${lwd.location.coordinates.lon}"
                distanceTextView.text = lwd.distance
            }
        }

        // Clear the values in the views to prevent incorrect data display when reused
        fun clear() {
            binding.apply {
                nameTextView.text = ""
                coordinatesTextView.text = ""
                distanceTextView.text = ""
            }
        }
    }



    class LocationDiffCallback : DiffUtil.ItemCallback<LocationWithDistance>() {
        override fun areItemsTheSame(oldItem: LocationWithDistance, newItem: LocationWithDistance): Boolean {
            // Compare unique identifiers based on name and coordinates
            return oldItem.location.name == newItem.location.name && oldItem.location.coordinates == newItem.location.coordinates
        }

        override fun areContentsTheSame(oldItem: LocationWithDistance, newItem: LocationWithDistance): Boolean {
            return oldItem == newItem
        }
    }
}

