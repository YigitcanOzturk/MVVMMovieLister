package com.yigitcan.mvvmmovielister.model

import com.google.gson.annotations.SerializedName


class Movie {
    @SerializedName("id")
    var id = 0

    @SerializedName("favorite_count")
    var favorite_count = 0

    @SerializedName("description")
    var description: String? = null

    @SerializedName("item_count")
    var item_count = 0

    @SerializedName("iso_639_1")
    var iso_639_1: String? = null

    @SerializedName("list_type")
    var list_type: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("poster_path")
    var poster_path: String? = null
}