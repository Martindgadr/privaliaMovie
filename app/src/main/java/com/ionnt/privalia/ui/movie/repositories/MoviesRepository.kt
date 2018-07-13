package com.ionnt.privalia.ui.movie.repositories

import com.ionnt.privalia.ui.movie.entities.*
import com.ionnt.privalia.ui.movie.services.MovieServices
import com.ionnt.privalia.utils.Constants.Companion.ACCESS_TOKEN
import com.ionnt.privalia.utils.Constants.Companion.API_KEY_FIELD
import com.ionnt.privalia.utils.Constants.Companion.BASE_FANART_URL
import com.ionnt.privalia.utils.Constants.Companion.CLIENT_KEY_FIELD
import com.ionnt.privalia.utils.Constants.Companion.CONTENT_TYPE
import com.ionnt.privalia.utils.Constants.Companion.EXTENDED_FIELD
import com.ionnt.privalia.utils.Constants.Companion.EXTENDED_INFO_TYPE
import com.ionnt.privalia.utils.Constants.Companion.FANART_API_KEY
import com.ionnt.privalia.utils.Constants.Companion.PAGE_FIELD
import com.ionnt.privalia.utils.Constants.Companion.QUERY_FIELD
import com.ionnt.privalia.utils.Constants.Companion.TRAKT_API_KEY
import com.ionnt.privalia.utils.Constants.Companion.TRAKT_API_VERSION
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import java.net.HttpURLConnection
import java.util.*
import javax.inject.Inject

/**
 * Created by Martin De Girolamo on 16/06/2018.
 */
class MoviesRepository @Inject constructor(retrofit: Retrofit) {
    private val moviesApi by lazy { retrofit.create(MovieServices::class.java) }
    var qtyItemsRequest: Int  = 0

    fun getPopularMovies(pageNumber: Int): Observable<List<Movie>> {
        // Getting popular movies from Trakt API
        return callApiPopularMovies(pageNumber)
                .map { processHeader(it) }
                .concatMap { httpValidation(it) }
                .flatMapIterable { movieList -> movieList }
                .flatMap { itemMovie -> callApiMovieImages(itemMovie.ids.tmdb.toString())
                        .subscribeOn(Schedulers.io())
                        .concatMap { httpValidation(it)
                                .map { mergeImageWithMovie(it, itemMovie) } }
                        .onErrorResumeNext(Observable.empty())
                }.toList().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun processHeader(header: Response<List<PopularMovies>>): Response<List<PopularMovies>> {
        header.headers()["X-Pagination-Item-Count"]?.let {
            qtyItemsRequest = it.toInt()
        }

        return header
    }

    private fun mergeImageWithMovie(imageUrl: ImageUrl, itemMovie: PopularMovies): Movie {
        return Movie(itemMovie.ids.trakt, itemMovie.title, itemMovie.year, imageUrl.movieposter!![0].url)
    }

    fun searchMovies(pageNumber: Int, query: String): Observable<List<MovieSearch>>{
        // Searching movies on Trakt Api
        return callApiSearchMovies(query, pageNumber)
                .map { processHeaderSearch(it) }
                .concatMap { httpValidation(it) }
                .flatMapIterable { movieList -> movieList }
                .flatMap { itemMovie -> callApiMovieImages(itemMovie.movie.ids.tmdb.toString())
                        .subscribeOn(Schedulers.io())
                        .concatMapDelayError { httpValidation(it)
                                .map { mergeImageWithMovieSearch(it, itemMovie) } }
                        .onErrorResumeNext(Observable.empty())
                }.toList().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun processHeaderSearch(header: Response<List<SearchMovies>>): Response<List<SearchMovies>> {
        header.headers()["X-Pagination-Item-Count"]?.let {
            qtyItemsRequest = it.toInt()
        }

        return header
    }

    private fun mergeImageWithMovieSearch(imageUrl: ImageUrl, itemMovie: SearchMovies): MovieSearch {
        return MovieSearch(itemMovie.movie.ids.trakt, itemMovie.movie.title, itemMovie.movie.year,
                imageUrl.movieposter!![0].url, itemMovie.movie.overview)
    }

    private fun callApiPopularMovies(pageNumber: Int): Observable<Response<List<PopularMovies>>> {
        val queryMap: MutableMap<String, String> = HashMap()
        queryMap[EXTENDED_FIELD] = EXTENDED_INFO_TYPE
        queryMap[PAGE_FIELD] = pageNumber.toString()

        return moviesApi.getMostPopularMovies(CONTENT_TYPE, TRAKT_API_KEY, TRAKT_API_VERSION, ACCESS_TOKEN, queryMap)
    }

    private fun callApiSearchMovies(query: String, pageNumber: Int): Observable<Response<List<SearchMovies>>>{
        val queryMap: MutableMap<String, String> = HashMap()
        queryMap[QUERY_FIELD] = query
        queryMap[PAGE_FIELD] = pageNumber.toString()
        queryMap[EXTENDED_FIELD] = EXTENDED_INFO_TYPE

        return moviesApi.getMovieSearch(CONTENT_TYPE, TRAKT_API_KEY, TRAKT_API_VERSION, queryMap)
    }

    private fun callApiMovieImages(movieId: String): Observable<Response<ImageUrl>>{
        val queryMapImage: MutableMap<String, String> = HashMap()
        queryMapImage[API_KEY_FIELD] = FANART_API_KEY
        queryMapImage[CLIENT_KEY_FIELD] = TRAKT_API_KEY

        return moviesApi.getMoviesImage(BASE_FANART_URL + movieId, queryMapImage)
    }

    private fun <T> httpValidation(response: Response<T>): Observable<T> {
        return Observable.fromCallable {
            if (response.isSuccessful) {
                response.body()?.let {
                    it
                }
            } else throw getExceptionHandler(response.code(), response.errorBody())
        }
    }

    private fun getExceptionHandler(code: Int, errorBody: ResponseBody?): Throwable {
        when(code) {
            HttpURLConnection.HTTP_NOT_FOUND -> throw Exception("Service request not found")
            HttpURLConnection.HTTP_UNAUTHORIZED -> throw Exception("User not authorized to access services")
            HttpURLConnection.HTTP_CONFLICT -> throw Exception("User token auth still available")
            else -> throw Exception("Unknown error")
        }
    }
}