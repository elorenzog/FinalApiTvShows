package com.elorenzog.finalprojecttvshows.service

import com.elorenzog.finalprojecttvshows.model.MostPopularTvShowsResponse
import com.elorenzog.finalprojecttvshows.model.ShowDetailsResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisoDateService {

    @GET("/api/most-popular")
    fun getMostPopularTvShows(@Query("page") page: Int = 1 ) : Call<MostPopularTvShowsResponse>

    @GET("/api/show-details")
    fun getShowDetails(@Query("q") tvShowId : Long) : Call<ShowDetailsResponse>

    companion object {
        private var _instance : EpisoDateService? = null

        fun getInstance() : EpisoDateService {
            if (_instance == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.episodate.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient.Builder().build())
                    .build()

                _instance = retrofit.create(EpisoDateService::class.java)
            }
            return _instance!!
        }
    }

}