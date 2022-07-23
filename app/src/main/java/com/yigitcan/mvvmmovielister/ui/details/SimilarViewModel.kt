package com.yigitcan.mvvmmovielister.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.yigitcan.mvvmmovielister.model.Movie
import com.yigitcan.mvvmmovielister.model.MovieResponseModel
import com.yigitcan.mvvmmovielister.service.SimilarAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SimilarViewModel : ViewModel(), Callback<MovieResponseModel?> {
    var similarMutableLiveData: MutableLiveData<ArrayList<Movie>?> = MutableLiveData()
    private var similarArrayList: ArrayList<Movie>? = null
    fun loadSimilarData() {
        if(Movie.selectedMovieId!=0) { // If movie is selected load data
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(Movie.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            val similarAPI = retrofit.create(SimilarAPI::class.java)
            val call: Call<MovieResponseModel?>? =
                similarAPI.loadChanges(Movie.selectedMovieId, "en-US", Movie.API_KEY)
            call?.enqueue(this)
        }
    }

    override fun onResponse(call: Call<MovieResponseModel?>?, response: Response<MovieResponseModel?>) {
        try {
            similarArrayList = ArrayList()
            similarArrayList = response.body()?.movie
            similarMutableLiveData.setValue(similarArrayList)
        }
        catch (e: NullPointerException) {
            e.printStackTrace()
            println(response.errorBody().toString() + "error")
        }
    }

    override fun onFailure(call: Call<MovieResponseModel?>?, t: Throwable) {
        t.printStackTrace()
    }

    init {

        // we call the Rest API in init method
        loadSimilarData()
    }
}