package com.yigitcan.mvvmmovielister.service

import com.yigitcan.mvvmmovielister.model.DetailsResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsAPI {
    @GET("movie/{movie_id}")
    fun loadChanges(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String?,
        @Query("api_key") apiKey: String?
    ): Call<DetailsResponseModel?>?
}