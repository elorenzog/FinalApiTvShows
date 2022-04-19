package com.elorenzog.finalprojecttvshows.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.elorenzog.finalprojecttvshows.R
import com.elorenzog.finalprojecttvshows.databinding.FragmentFavoriteTvShowListBinding
import com.elorenzog.finalprojecttvshows.model.FavoriteTvShow
import com.elorenzog.finalprojecttvshows.model.TvShow
import com.elorenzog.finalprojecttvshows.model.TvShowListAdapter
import com.elorenzog.finalprojecttvshows.viewmodel.EpisoDateViewModel
import com.elorenzog.finalprojecttvshows.viewmodel.FavoriteShowViewModel
import com.google.android.material.snackbar.Snackbar


class FavoriteTvShowListFragment : Fragment() {
    private lateinit var binding : FragmentFavoriteTvShowListBinding
    private lateinit var list: MutableList<TvShow>
    private lateinit var listFav: MutableList<TvShow>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFavoriteTvShowListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding.favoriteTvShowsList.layoutManager = LinearLayoutManager(requireContext())
        val viewModelFavorite = ViewModelProvider(requireActivity()).get(FavoriteShowViewModel::class.java)
        val viewModel = ViewModelProvider(requireActivity()).get(EpisoDateViewModel::class.java)
        list = mutableListOf<TvShow>()
        listFav = mutableListOf<TvShow>()

        viewModel.tvShowList.observe(viewLifecycleOwner) {

            list.addAll(it)

//            binding.favoriteTvShowsList.adapter = TvShowListAdapter(it, viewModel);
        }

        viewModelFavorite.favoriteShowList.observe(viewLifecycleOwner) {
            it.forEach {
                list.forEach { show ->
                    if (show.id == it.tvShowId.toLong()) {
                        listFav.add(show)
                    }
                }
            }
            binding.favoriteTvShowsList.adapter = TvShowListAdapter(listFav, viewModel)
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
        viewModelFavorite.loadFavoriteShows()

        return binding.root
//        inflater.inflate(R.layout.fragment_favorite_tv_show_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteTvShowListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteTvShowListFragment().apply {

            }
    }
}