package com.elorenzog.finalprojecttvshows.service

import com.elorenzog.finalprojecttvshows.model.FavoriteShowsResponse
import com.elorenzog.finalprojecttvshows.model.FavoriteTvShow
import com.google.firebase.auth.FirebaseAuth
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface FavoriteShowsService {

    @Headers("Accept: application/json")
    @GET("api/favorites/{userId}")
    fun getFavoritesShows(@Path("userId")userId: String = "") : Call<FavoriteShowsResponse>

    @POST("/api/favorites")
    fun postFavoriteShow(@Body favoriteShow : FavoriteTvShow)

    companion object {
        private var _instance: FavoriteShowsService? = null

        fun getInstance(): FavoriteShowsService {
            if (_instance == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://apifavorite.azurewebsites.net")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient.Builder().build())
                    .build()

                _instance = retrofit.create(FavoriteShowsService::class.java)
            }
            return _instance!!
        }

    }

}