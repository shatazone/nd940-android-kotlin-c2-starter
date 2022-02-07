package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import java.lang.IllegalArgumentException


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val repository = AsteroidRepository(AsteroidDatabase.getInstance(requireContext()), Network.NASA_API)
        ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        val asteroidListAdapter = AsteroidListAdapter(AsteroidClickListener {
            viewModel.displayAsteroidDetails(it)
        })

        binding.asteroidRecycler.adapter = asteroidListAdapter

        setHasOptionsMenu(true)

        viewModel.asteroids.observe(viewLifecycleOwner) {
            asteroidListAdapter.submitList(it)
        }

        viewModel.displayAsteroidDetails.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onAsteroidDetailsDisplayedCompleted()
            }
        }

        viewModel.displayConnectionError.observe(viewLifecycleOwner) {
            if (it == true) {
                Snackbar.make(requireView(), "Please check your connection", Snackbar.LENGTH_LONG).show()
                viewModel.onDisplayConnectionErrorCompleted()
            }
        }
        binding.swiperefresh.isNestedScrollingEnabled = true

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.show_week_menu -> viewModel.showWeekAsteroids()
            R.id.show_today_menu -> viewModel.showTodayAsteroids()
            R.id.show_saved_menu -> viewModel.showAllAsteroids()
            else -> return false
        }

        return true
    }
}
