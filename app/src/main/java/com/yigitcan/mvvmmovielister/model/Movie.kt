package com.yigitcan.mvvmmovielister.model

import com.google.gson.annotations.SerializedName


class Movie {

    companion object {
        var selectedMovieId = 0
    }

    @SerializedName("id")
    var videoId = 0

    @SerializedName("vote_count")
    var voteCount = 0

    @SerializedName("release_date")
    var releaseDate: String? = null

    @SerializedName("vote_average")
    var voteAverage = 0.00

    @SerializedName("title")
    var title: String? = null

    @SerializedName("overview")
    var overView: String? = null

    @SerializedName("poster_path")
    var posterPath: String? = null
}