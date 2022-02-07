package com.udacity.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.database.entities.AsteroidEntity
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding

class AsteroidListAdapter(private val listener: AsteroidClickListener) :
    ListAdapter<AsteroidEntity, AsteroidViewHolder>(ItemCallback()) {
    class ItemCallback : DiffUtil.ItemCallback<AsteroidEntity>() {
        override fun areItemsTheSame(oldItem: AsteroidEntity, newItem: AsteroidEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AsteroidEntity, newItem: AsteroidEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class AsteroidViewHolder private constructor(
    private val binding: AsteroidListItemBinding,
    private val listener: AsteroidClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(asteroid: AsteroidEntity) {
        binding.asteroid = asteroid
        binding.listener = listener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup, listener: AsteroidClickListener): AsteroidViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = AsteroidListItemBinding.inflate(inflater, parent, false)
            return AsteroidViewHolder(binding, listener)
        }
    }
}

class AsteroidClickListener(val listener: (asteroid: AsteroidEntity) -> Unit) {
    fun onClick(asteroid: AsteroidEntity) = listener(asteroid)
}