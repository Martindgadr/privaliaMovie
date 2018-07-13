package com.ionnt.privalia.ui.movie.services

import com.ionnt.privalia.ui.movie.entities.ImageUrl
import com.ionnt.privalia.ui.movie.entities.PopularMovies
import com.ionnt.privalia.ui.movie.entities.SearchMovies
import com.ionnt.privalia.utils.Constants.Companion.MOST_POPULAR_REQUEST
import com.ionnt.privalia.utils.Constants.Companion.MOVIE_SEARCH_REQUEST
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * Created by Martin De Girolamo on 16/06/2018.
 */
interface MovieServices {
    @GET(MOST_POPULAR_REQUEST)
    fun getMostPopularMovies(@Header("Content-Type") contentType: String,
                             @Header("trakt-api-key") traktApiKey: String,
                             @Header("trakt-api-version") traktApiVersion: String,
                             @Header("Authorization") authToken: String,
                             @QueryMap query: Map<String, String>):
            Observable<Response<List<PopularMovies>>>

    @GET(MOVIE_SEARCH_REQUEST)
    fun getMovieSearch(@Header("Content-Type") contentType: String,
                       @Header("trakt-api-key") traktApiKey: String,
                       @Header("trakt-api-version") traktApiVersion: String,
                       @QueryMap query: Map<String, String>): Observable<Response<List<SearchMovies>>>

    @GET
    fun getMoviesImage(@Url url: String, @QueryMap query: Map<String, String>) : Observable<Response<ImageUrl>>
}