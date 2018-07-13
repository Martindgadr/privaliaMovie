package com.ionnt.privalia.ui.movie.entities

/**
 * Created by Martin De Girolamo on 23/06/2018.
 */

data class MovieSearch(
        val id: Int,
        val title: String,
        val year: Int,
        val photoUrl: String?,
        val overview: String?
){
    constructor(): this(0,"",0, "", "")
}