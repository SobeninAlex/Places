package com.example.places.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.places.R
import com.example.places.data.model.Place
import com.example.places.databinding.FragmentAddPlaceBinding
import com.example.places.ui.viewmodel.PlaceViewModel

class AddPlaceFragment : Fragment() {

    private val navigationArgs: AddPlaceFragmentArgs by navArgs()

    private var _binding: FragmentAddPlaceBinding? = null

    private lateinit var place: Place
    private val binding get() = _binding!!

    private val viewModel: PlaceViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        if (id > 0) {
            viewModel.getPlaceById(id).observe(viewLifecycleOwner) {
                place = it
                bindPlace(place)
            }

            binding.deleteBtn.visibility = View.VISIBLE
            binding.deleteBtn.setOnClickListener {
                deletePlace(place)
            }
        } else {
            binding.saveBtn.setOnClickListener {
                addPlace()
            }
        }
    }

    private fun deletePlace(place: Place) {
        viewModel.deletePlace(place)
        findNavController().navigate(
            R.id.action_addPlaceFragment_to_placeListFragment
        )
    }

    private fun addPlace() {
        if (isValidEntry()) {
            viewModel.addPlace(
                binding.nameInput.text.toString(),
                binding.locationAddressInput.text.toString(),
                binding.notesInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addPlaceFragment_to_placeListFragment
            )
        }
    }

    private fun updatePlace() {
        if (isValidEntry()) {
            viewModel.updatePlace(
                id = navigationArgs.id,
                name = binding.nameInput.text.toString(),
                address = binding.locationAddressInput.text.toString(),
                notes = binding.notesInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addPlaceFragment_to_placeListFragment
            )
        }
    }

    private fun bindPlace(place: Place) {
        binding.apply{
            nameInput.setText(place.name, TextView.BufferType.SPANNABLE)
            locationAddressInput.setText(place.address, TextView.BufferType.SPANNABLE)
            notesInput.setText(place.notes, TextView.BufferType.SPANNABLE)
            saveBtn.setOnClickListener {
                updatePlace()
            }
        }
    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.nameInput.text.toString(),
        binding.locationAddressInput.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
