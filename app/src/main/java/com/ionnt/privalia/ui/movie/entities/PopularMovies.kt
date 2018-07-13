package com.ionnt.privalia.ui.movie.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Martin De Girolamo on 15/06/2018.
 */
data class PopularMovies(
        val title: String,
        val year: Int,
        val ids: Ids,
        @field:SerializedName("overview")
        val overview: String,
        var imageUrl: String
)

data class Ids(
        val trakt: Int,
        val slug: String,
        val imdb: String,
        val tmdb: Int
)
