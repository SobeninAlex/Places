package com.example.places.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.places.data.model.Place
import com.example.places.databinding.FragmentPlaceDetailBinding
import com.example.places.ui.viewmodel.PlaceViewModel

class PlaceDetailFragment : Fragment() {

    private val navigationArgs: PlaceDetailFragmentArgs by navArgs()

    private val viewModel: PlaceViewModel by activityViewModels()

    private lateinit var place: Place

    private var _binding: FragmentPlaceDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.getPlaceById(id).observe(viewLifecycleOwner) {
            place = it
            bindPlace()
        }
    }

    private fun bindPlace() {
        binding.apply {
            name.text = place.name
            location.text = place.address
            notes.text = place.notes
            editPlaceFab.setOnClickListener {
                val action = PlaceDetailFragmentDirections
                    .actionPlaceDetailFragmentToAddPlaceFragment(place.id)
                findNavController().navigate(action)
            }

            location.setOnClickListener {
                launchMap()
            }
        }
    }

    private fun launchMap() {
        val address = place.address.let {
            it.replace(", ", ",")
            it.replace(". ", " ")
            it.replace(" ", "+")
        }
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}
