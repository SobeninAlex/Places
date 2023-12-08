package com.example.places.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.places.R
import com.example.places.databinding.FragmentPlaceListBinding
import com.example.places.ui.adapter.PlaceListAdapter
import com.example.places.ui.viewmodel.PlaceViewModel

class PlaceListFragment : Fragment() {

    private val viewModel: PlaceViewModel by activityViewModels()

    private var _binding: FragmentPlaceListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlaceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PlaceListAdapter { place ->
            val action = PlaceListFragmentDirections
                .actionPlaceListFragmentToPlaceDetailFragment(place.id)
            findNavController().navigate(action)
        }

        viewModel.places.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {
            recyclerView.adapter = adapter
            addPlaceFab.setOnClickListener {
                findNavController().navigate(R.id.action_placeListFragment_to_addPlaceFragment)
            }
        }
    }
}
