package com.ionnt.privalia.ui.movie.entities

/**
 * Created by Martin De Girolamo on 23/06/2018.
 */

data class SearchMovies(val type: String,
                        val score: Float,
                        val movie: PopularMovies
)