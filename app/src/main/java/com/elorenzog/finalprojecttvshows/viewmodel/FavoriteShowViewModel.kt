package com.elorenzog.finalprojecttvshows.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elorenzog.finalprojecttvshows.model.FavoriteShowsResponse
import com.elorenzog.finalprojecttvshows.model.FavoriteTvShow
import com.elorenzog.finalprojecttvshows.model.TvShow
import com.elorenzog.finalprojecttvshows.service.EpisoDateService
import com.elorenzog.finalprojecttvshows.service.FavoriteShowsService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteShowViewModel() : ViewModel() {

    var service = FavoriteShowsService.getInstance()
    private val _favoriteShowList: MutableLiveData<List<FavoriteTvShow>> = MutableLiveData()
    val favoriteShowList: LiveData<List<FavoriteTvShow>> = _favoriteShowList
    private val  _favoriteShow: MutableLiveData<FavoriteTvShow> = MutableLiveData()
    private val _error : MutableLiveData<String> = MutableLiveData()
    private val _selected : MutableLiveData<FavoriteTvShow> = MutableLiveData()
    val tvShowDetail : LiveData<FavoriteTvShow> = _favoriteShow
    val user = FirebaseAuth.getInstance().currentUser!!.email

    val error : LiveData<String> = _error
    val selected : LiveData<FavoriteTvShow> = _selected

    fun loadFavoriteShows() {
        viewModelScope.launch {
            service.getFavoritesShows(user.toString()).enqueue(object : Callback<FavoriteShowsResponse> {
                override fun onResponse(
                    call: Call<FavoriteShowsResponse>,
                    response: Response<FavoriteShowsResponse>
                ) {
                    _favoriteShowList.postValue(response.body()!!.favoriteTvShows)
                    Log.i("responseFav", response.body()!!.favoriteTvShows?.get(0)?.userId.toString())

                }

                override fun onFailure(call: Call<FavoriteShowsResponse>, t: Throwable) {
                    _error.postValue(t.message)
                }

            })
        }
    }

    fun setSelectedItem(favoriteShow : FavoriteTvShow) {
        _selected.value = favoriteShow
    }
}