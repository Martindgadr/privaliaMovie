package com.ionnt.privalia.ui.movie.entities

/**
 * Created by Martin De Girolamo on 15/06/2018.
 */
data class Movie (
        val id: Int,
        val title: String,
        val year: Int,
        val photoUrl: String
){
    constructor(): this(0,"",0, "")
}