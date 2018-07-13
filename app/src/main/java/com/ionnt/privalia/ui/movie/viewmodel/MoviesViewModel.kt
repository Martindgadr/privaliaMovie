package com.ionnt.privalia.ui.movie.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ionnt.privalia.commons.utils.NetworkHandler
import com.ionnt.privalia.ui.movie.entities.Movie
import com.ionnt.privalia.ui.movie.entities.MovieSearch
import com.ionnt.privalia.ui.movie.repositories.MoviesRepository
import javax.inject.Inject

/**
 * Created by Martin De Girolamo on 17/06/2018.
 */
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository,
                                          private val networkHandler: NetworkHandler): ViewModel() {
    var mostPopularMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    var moviesSearchShow: MutableLiveData<List<MovieSearch>> = MutableLiveData()
    var showHideProgress: MutableLiveData<Boolean> = MutableLiveData()
    var errorConnection: MutableLiveData<Boolean> = MutableLiveData()
    var errorService: MutableLiveData<Boolean> = MutableLiveData()

    private var lastPageTaken: Int = 0
    private var lastPageTakenSearch: Int = 0
    var qtyItemsToShow: Int = 0

    fun getPopularMovies(){
        when (networkHandler.isConnected) {
            true -> { lastPageTaken += 1
                showHideProgress.value = true
                moviesRepository.getPopularMovies(lastPageTaken).subscribe(this::handleResponse, this::handleError)}
            else -> errorConnection.value = true
        }
    }

    fun searchMovies(query: String) {
        when (networkHandler.isConnected){
            true -> {
                if(query.isNotEmpty()) {
                    lastPageTakenSearch += 1
                    showHideProgress.value = true
                    moviesRepository.searchMovies(lastPageTakenSearch, query).subscribe(this::handleResponseSearch, this::handleError)}
                }
            else -> errorConnection.value = true
        }
    }

    private fun handleResponse(movieList: List<Movie>) {
        showHideProgress.value = false
        qtyItemsToShow = moviesRepository.qtyItemsRequest
        mostPopularMovies.value = movieList
    }

    private fun handleResponseSearch(movieList: List<MovieSearch>) {
        moviesSearchShow.value = movieList
        qtyItemsToShow = moviesRepository.qtyItemsRequest
        showHideProgress.value = false
    }

    private fun handleError(error: Throwable){
        showHideProgress.value = false
        Log.e(MoviesViewModel::class.java.name, error.message + error.cause)
        errorService.value = true
    }

    fun increaseSearchPageCounter(){
        lastPageTakenSearch = 0
    }
}