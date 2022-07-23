package com.yigitcan.mvvmmovielister

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.yigitcan.mvvmmovielister.model.Movie
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

class FilterTest {

    private lateinit var filterList: ArrayList<Movie>
    private lateinit var movieList: ArrayList<Movie>
    private val jsonString: String = "[\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"Go behind the scenes during One Directions sell out \\\"Take Me Home\\\" tour and experience life on the road.\",\n" +
            "      \"release_date\": \"2013-08-30\",\n" +
            "      \"genre_ids\": [\n" +
            "        99,\n" +
            "        10402\n" +
            "      ],\n" +
            "      \"id\": 164558,\n" +
            "      \"original_title\": \"One Direction: This Is Us\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"One Direction: This Is Us\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.166982,\n" +
            "      \"vote_count\": 55,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 8.45\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"\",\n" +
            "      \"release_date\": \"1954-06-22\",\n" +
            "      \"genre_ids\": [\n" +
            "        80,\n" +
            "        18\n" +
            "      ],\n" +
            "      \"id\": 654,\n" +
            "      \"original_title\": \"On the Waterfront\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"On the Waterfront\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.07031,\n" +
            "      \"vote_count\": 51,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 8.19\n" +
            "    }" +
            "]"

    @Before
    fun setup() {
        val gson = GsonBuilder().create()
        movieList = gson.fromJson(jsonString, object : TypeToken<ArrayList<Movie>>() {}.type)
        filterList = movieList
    }

    @Test
    fun filter() {
        val constraint = "On the Waterfront" // expected title must be first object after filtering
        val resultList = ArrayList<Movie>()
        for (row in movieList) {
            if (row.originalTitle!!.lowercase(Locale.getDefault())
                    .contains(constraint.lowercase(Locale.getDefault()))
            ) {
                resultList.add(row)
            }
        }
        filterList = resultList
        Assert.assertEquals(constraint,filterList[0].originalTitle)
    }
}