package com.mik2003.boukennihon.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mik2003.boukennihon.core.Location
import com.mik2003.boukennihon.databinding.ItemLocationBinding


class LocationsAdapter : ListAdapter<Location, LocationsAdapter.LocationViewHolder>(LocationDiffCallback()) {

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
        fun bind(location: Location) {
            binding.apply {
                // Bind location data to views
                nameTextView.text = location.name
                coordinatesTextView.text = "${location.coordinates.lat}, ${location.coordinates.lon}"
            }
        }

        // Clear the values in the views to prevent incorrect data display when reused
        fun clear() {
            binding.apply {
                nameTextView.text = ""
                coordinatesTextView.text = ""
            }
        }
    }



    class LocationDiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            // Compare unique identifiers based on name and coordinates
            return oldItem.name == newItem.name && oldItem.coordinates == newItem.coordinates
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }
}

