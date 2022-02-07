package com.udacity.asteroidradar.ui.datafetcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.databinding.FragmentDataFetcherBinding

class DataFetcherFragment : Fragment() {
    private lateinit var viewModel: DataFetcherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val nasaApiService = AsteroidRepository(AsteroidDatabase.getInstance(requireContext()), Network.NASA_API)
        viewModel = ViewModelProviders.of(this, DataFetcherViewModel.Factory(nasaApiService)).get(DataFetcherViewModel::class.java)

        viewModel.navigateToMainScreen.observe(viewLifecycleOwner) {
            if(it) {
                findNavController().navigate(DataFetcherFragmentDirections.actionDataFetcherFragment2ToMainFragment())
            }
        }

        val binding = FragmentDataFetcherBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}