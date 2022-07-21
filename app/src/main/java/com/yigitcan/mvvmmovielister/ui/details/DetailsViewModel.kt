package com.yigitcan.mvvmmovielister.ui.details

import android.view.View
import androidx.lifecycle.*
import com.google.gson.GsonBuilder
import com.yigitcan.mvvmmovielister.model.DetailsResponseModel
import com.yigitcan.mvvmmovielister.model.Genre
import com.yigitcan.mvvmmovielister.model.Movie
import com.yigitcan.mvvmmovielister.service.DetailsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsViewModel : ViewModel(), Callback<DetailsResponseModel?> {
    var detailsMutableLiveData: MutableLiveData<ArrayList<Genre>?> = MutableLiveData()
    private var genreArrayList: ArrayList<Genre>? = null
    private val _select1= MutableLiveData<Int>()
    private val _select2= MutableLiveData<Int>()
    val select1: LiveData<Int> = _select1
    val select2: LiveData<Int> = _select2

    fun loadData() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Movie.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val detailsAPI = retrofit.create(DetailsAPI::class.java)
        val call: Call<DetailsResponseModel?>? =
            detailsAPI.loadChanges(Movie.selectedMovieId, "en-US", Movie.API_KEY)
        call?.enqueue(this)
    }

    override fun onResponse(
        call: Call<DetailsResponseModel?>?,
        response: Response<DetailsResponseModel?>
    ) {
        try {
            genreArrayList = ArrayList()
            genreArrayList = response.body()?.genre
            val lastIndex: Int = genreArrayList!!.size - 1
            genreArrayList!![lastIndex].posterPath = response.body()?.posterPath // index[0] is not stable but last index works
            genreArrayList!![lastIndex].overView = response.body()?.overView
            genreArrayList!![lastIndex].releaseDate = response.body()?.releaseDate
            genreArrayList!![lastIndex].voteAverage = response.body()?.voteAverage
            genreArrayList!![lastIndex].status = response.body()?.status
            genreArrayList!![lastIndex].voteCount = response.body()?.voteCount
            genreArrayList!![lastIndex].title = response.body()?.title
            genreArrayList!![lastIndex].videoID = response.body()?.id
            detailsMutableLiveData.value = genreArrayList
        } catch (e: Exception) {
            e.printStackTrace()
            println(response.errorBody().toString() + "error")
        }
    }

    override fun onFailure(call: Call<DetailsResponseModel?>?, t: Throwable) {
        t.printStackTrace()
    }

    init {

        // we call the Rest API in init method
        loadData()
        control1()
        control2()
    }



    fun control1 (){
        _select1.value= if(Movie.selectedMovieId==0) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun control2 (){
        _select2.value= if(Movie.selectedMovieId==0) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}
