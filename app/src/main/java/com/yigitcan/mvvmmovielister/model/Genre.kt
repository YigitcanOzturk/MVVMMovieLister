package com.yigitcan.mvvmmovielister.model

import com.google.gson.annotations.SerializedName

class Genre {
    @SerializedName("id")
    var genreId: Int? = 0

    @SerializedName("name")
    var genreName: String? = null

    var title: String? = null
    var voteCount: Int? = 0
    var voteAverage: Double? = 0.00
    var releaseDate: String? = null
    var status: String? = null
    var overView: String? = null
    var videoID: Int? = 0
    var posterPath: String? = null
}