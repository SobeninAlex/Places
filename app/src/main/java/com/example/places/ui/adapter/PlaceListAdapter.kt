package com.example.places.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.places.data.model.Place
import com.example.places.databinding.ListItemPlaceBinding

class PlaceListAdapter(
    private val clickListener: (Place) -> Unit
) : ListAdapter<Place, PlaceListAdapter.PlaceViewHolder>(DiffCallback) {

    class PlaceViewHolder(
        private var binding: ListItemPlaceBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {
            binding.place = place
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback: DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlaceViewHolder(
            ListItemPlaceBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = getItem(position)
        holder.itemView.setOnClickListener{
            clickListener(place)
        }
        holder.bind(place)
    }

}
