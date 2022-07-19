package com.yigitcan.mvvmmovielister.model

import com.google.gson.annotations.SerializedName

class MovieResponseModel {
    @SerializedName("id")
    var id = 0

    @SerializedName("page")
    var page = 0

    @SerializedName("total_pages")
    var total_pages = 0

    @SerializedName("total_results")
    var total_results = 0

    @SerializedName("results")
    var movie: ArrayList<Movie>? = null
}