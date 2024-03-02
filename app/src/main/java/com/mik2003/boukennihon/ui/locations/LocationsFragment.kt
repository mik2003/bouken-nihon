package com.mik2003.boukennihon.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mik2003.boukennihon.core.ParseKML
import com.mik2003.boukennihon.databinding.FragmentLocationsBinding
import com.mik2003.boukennihon.ui.adapter.LocationsAdapter
import com.mik2003.boukennihon.R
import com.mik2003.boukennihon.core.LocationUtils
import com.mik2003.boukennihon.core.Coordinate

import android.util.Log

class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null
    private lateinit var locationsViewModel: LocationsViewModel
    private lateinit var locationsAdapter: LocationsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        locationsViewModel =
            ViewModelProvider(this).get(LocationsViewModel::class.java)

        // Set up RecyclerView
        locationsAdapter = LocationsAdapter()
        binding.recyclerView.apply {
            adapter = locationsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Observe locations from ViewModel
        locationsViewModel.locationsWithDistance.observe(viewLifecycleOwner) { locationsWithDistance ->
            // Update UI accordingly
            locationsAdapter.submitList(locationsWithDistance)
        }


        // Parse the KML file and update ViewModel
        val inputStream = resources.openRawResource(R.raw.nihon)
        val locationsList = ParseKML.parseKML(inputStream)

        // Initialize FusedLocationProviderClient
        LocationUtils.initializeFusedLocationProvider(requireContext())

        // Request location permission if not granted
        LocationUtils.getLastKnownLocation(requireContext(),
            onSuccess = { location ->
                Log.d("Location", "YES")
                // Location retrieved successfully, now parse KML and update ViewModel
                val currentCoordinate = Coordinate(location.latitude, location.longitude)
                val distancesMap = locationsList.distancesFromCurrentLocation(currentCoordinate)

                if (distancesMap.isNotEmpty()) {
                    // Distance data is available, set locations with distances
                    locationsViewModel.setLocationsWithDistances(locationsList, distancesMap)
                } else {
                    Log.d("Location", "NO")
                }
            },
            onFailure = {
                Log.d("Location", "NO")
            }
        )


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
