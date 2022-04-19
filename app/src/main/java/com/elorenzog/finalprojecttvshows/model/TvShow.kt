package com.elorenzog.finalprojecttvshows.model

import com.google.gson.annotations.SerializedName

class TvShow {
    var id: Long = 0
    var name: String = ""

    @SerializedName("image_thumbnail_path")
    var image: String = ""

    @SerializedName("start_date")
    var startDate: String =""

    @SerializedName("end_date")
    var endDate: String =""

    var country: String = ""
    var network: String = ""
    var status: String = ""
    var description: String = ""
}

class FavoriteTvShow {
    var id : Int = 0
    var userId : String = ""
    var tvShowId : String = ""
}

class MostPopularTvShowsResponse {
    var total: Int = 0
    var page: Int = 1
    var pages: Int = 0

    @SerializedName("tv_shows")
    var tvShows: List<TvShow>? = null
}

class ShowDetailsResponse {
    var tvShow: TvShow? = null
}

class FavoriteShowsResponse {
    var favoriteTvShows: List<FavoriteTvShow>? = null
}