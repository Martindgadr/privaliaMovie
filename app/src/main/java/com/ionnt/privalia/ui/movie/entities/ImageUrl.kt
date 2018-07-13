package com.ionnt.privalia.ui.movie.entities

/**
 * Created by Martin De Girolamo on 20/06/2018.
 */
data class ImageUrl(
    val name: String,
    val tmdb_id: String,
    val imdb_id: String,
    val movieposter: List<Movieposter>?
)

data class Movieposter(
    val id: String,
    val url: String,
    val lang: String,
    val likes: String
)