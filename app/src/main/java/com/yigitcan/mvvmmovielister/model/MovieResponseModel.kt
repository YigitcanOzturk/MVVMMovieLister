package com.yigitcan.mvvmmovielister.model

import com.google.gson.annotations.SerializedName

class MovieResponseModel {

    @SerializedName("page")
    var page = 0

    @SerializedName("total_pages")
    var totalPages = 0

    @SerializedName("total_results")
    var totalResults = 0

    @SerializedName("results")
    var movie: ArrayList<Movie>? = null
}