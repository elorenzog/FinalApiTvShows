package com.elorenzog.finalprojecttvshows.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elorenzog.finalprojecttvshows.model.MostPopularTvShowsResponse
import com.elorenzog.finalprojecttvshows.model.ShowDetailsResponse
import com.elorenzog.finalprojecttvshows.model.TvShow
import com.elorenzog.finalprojecttvshows.service.EpisoDateService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisoDateViewModel() : ViewModel() {

    var service = EpisoDateService.getInstance()
    private val _tvShowList: MutableLiveData<List<TvShow>> = MutableLiveData()
    val tvShowList: LiveData<List<TvShow>> = _tvShowList
    private  val  _tvShow: MutableLiveData<TvShow> = MutableLiveData()
    private val _error : MutableLiveData<String> = MutableLiveData()
    private val _selected : MutableLiveData<TvShow> = MutableLiveData()
    val tvShowDetail : LiveData<TvShow> = _tvShow

    val error :LiveData<String> = _error
    val selected :LiveData<TvShow> = _selected



    fun loadTvShows() {

        viewModelScope.launch {
            service.getMostPopularTvShows().enqueue(object : Callback<MostPopularTvShowsResponse>{
                override fun onResponse(
                    call: Call<MostPopularTvShowsResponse>,
                    response: Response<MostPopularTvShowsResponse>
                ) {
                    _tvShowList.postValue(response.body()!!.tvShows)
                    Log.i("response", response.body()!!.tvShows?.get(0)?.name.toString())
                }

                override fun onFailure(call: Call<MostPopularTvShowsResponse>, t: Throwable) {
                    _error.postValue(t.message)
                }


            })
        }
    }

    fun setSelectedItem(tvShow: TvShow) {
        _selected.value = tvShow
    }

    fun loadDetail(value: TvShow?) {
        CoroutineScope(Dispatchers.IO).launch {
            service.getShowDetails(selected.value!!.id).enqueue(object : Callback<ShowDetailsResponse> {
                override fun onResponse(
                    call: Call<ShowDetailsResponse>,
                    response: Response<ShowDetailsResponse>
                ) {
                    _tvShow.postValue(response.body()!!.tvShow)
                }

                override fun onFailure(call: Call<ShowDetailsResponse>, t: Throwable) {
                    _error.postValue(t.message)
                }

            })
        }
    }
}