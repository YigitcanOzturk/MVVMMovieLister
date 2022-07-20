package com.yigitcan.mvvmmovielister.model

import com.google.gson.annotations.SerializedName

class Genre {
    @SerializedName("id")
    var genreId: Int? = null

    @SerializedName("name")
    var genreName: String? = null

    var title: String? = null
    var voteCount: Int? = null
    var voteAverage: Double? = null
    var releaseDate: String? = null
    var status: String? = null
    var overView: String? = null
    var videoID: Int? = null
    var posterPath: String? = null
}