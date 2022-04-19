package com.elorenzog.finalprojecttvshows.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.elorenzog.finalprojecttvshows.R
import com.elorenzog.finalprojecttvshows.databinding.FragmentTvShowDetailBinding
import com.elorenzog.finalprojecttvshows.viewmodel.EpisoDateViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TvShowDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TvShowDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentTvShowDetailBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(EpisoDateViewModel::class.java)

        viewModel.tvShowDetail.observe(viewLifecycleOwner) {
            binding.detailText.text = it.description
        }

        viewModel.loadDetail(viewModel.selected.value)
        viewModel.setSelectedItem(null)

        return binding.root
//        inflater.inflate(R.layout.fragment_tv_show_detail, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        val fragment = LoginFragment()
        val transaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    override fun onDestroy() {
        super.onDestroy()
        val fragment = LoginFragment()
        val transaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TvShowDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TvShowDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}