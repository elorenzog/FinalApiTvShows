package com.elorenzog.finalprojecttvshows.model

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elorenzog.finalprojecttvshows.R
import com.elorenzog.finalprojecttvshows.databinding.TvShowListItemBinding
import com.elorenzog.finalprojecttvshows.viewmodel.EpisoDateViewModel
import com.squareup.picasso.Picasso

class TvShowListAdapter(val tvShows: List<TvShow>, val viewModel: EpisoDateViewModel) : RecyclerView.Adapter<TvShowListAdapter.ViewHolder>() {
    public class ViewHolder(binding: TvShowListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TvShowListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tvShow = tvShows[position]
        val picasso = Picasso.get()
                        .load(tvShow.image)
                        .placeholder(R.drawable.cinemovil)
                        .centerCrop()
                        .resize(80 * 4, 100 * 4)
                        .into(holder.binding.tvShowPoster)
        holder.binding.tvShowName.text = tvShow.name
        holder.binding.tvShowDate.text = tvShow.startDate
        holder.binding.tvShowSource.text = tvShow.network
        holder.binding.tvShowStatus.text = tvShow.status
        when (tvShow.status) {
            "Ended" -> {
                holder.binding.tvShowStatus.setTextColor(Color.parseColor("#ff0018"))
            }
            "Running" -> {
                holder.binding.tvShowStatus.setTextColor(Color.parseColor("#75a184"))
            }
            "To Be Determined" -> {
                holder.binding.tvShowStatus.setTextColor(Color.parseColor("#fed800"))
            }
        }
        holder.binding.root.setOnClickListener {
            viewModel.setSelectedItem(tvShow)
        }
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

}