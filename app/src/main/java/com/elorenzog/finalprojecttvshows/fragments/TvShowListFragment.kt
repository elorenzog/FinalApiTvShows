package com.elorenzog.finalprojecttvshows.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.elorenzog.finalprojecttvshows.R
import com.elorenzog.finalprojecttvshows.databinding.FragmentTvShowListBinding
import com.elorenzog.finalprojecttvshows.model.TvShow
import com.elorenzog.finalprojecttvshows.model.TvShowListAdapter
import com.elorenzog.finalprojecttvshows.viewmodel.EpisoDateViewModel
import com.google.android.material.snackbar.Snackbar

class TvShowListFragment : Fragment() {

    private lateinit var binding : FragmentTvShowListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTvShowListBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.tvShowsList.layoutManager = LinearLayoutManager(requireContext())
        val viewModel = ViewModelProvider(requireActivity()).get(EpisoDateViewModel::class.java)
        viewModel.tvShowList.observe(viewLifecycleOwner) {
            binding.tvShowsList.adapter = TvShowListAdapter(it, viewModel);
        }

        binding.buttonProfile.setOnClickListener {
            val fragment =ProfileFragment()
            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.buttonFavorite.setOnClickListener {
            val fragment = FavoriteTvShowListFragment()
            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        viewModel.selected.observe(viewLifecycleOwner) {
            val fragment = TvShowDetailFragment()
            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.addToBackStack(null)
            transaction.commit()


        }


        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.loadTvShows()

        return binding.root
//        inflater.inflate(R.layout.fragment_tv_show_list, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TvShowListFragment().apply {

            }
    }
}