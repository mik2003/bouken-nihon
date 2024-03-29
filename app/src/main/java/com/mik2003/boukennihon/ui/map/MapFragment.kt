package com.mik2003.boukennihon.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.webkit.WebView
import com.mik2003.boukennihon.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val webView: WebView = binding.webView

        webView.loadUrl("https://www.google.com/maps/d/edit?mid=18yINesZ0_bjv9w6DCQT6PmHRiph5eRM&usp=sharing")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}