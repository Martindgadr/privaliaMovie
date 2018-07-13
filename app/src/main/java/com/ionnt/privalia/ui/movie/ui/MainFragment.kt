package com.ionnt.privalia.ui.movie.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.ionnt.privalia.R
import com.ionnt.privalia.R.id.action_search
import com.ionnt.privalia.commons.base.BaseFragment
import com.ionnt.privalia.commons.factory.RVAdapterFactory
import com.ionnt.privalia.commons.recyclerview.adapter.GenericAdapter
import com.ionnt.privalia.ui.movie.entities.Movie
import com.ionnt.privalia.ui.movie.entities.MovieSearch
import com.ionnt.privalia.ui.movie.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies.view.*

/**
 * Created by Martin De Girolamo on 16/06/2018.
 */
class MainFragment: BaseFragment() {
    private lateinit var viewModel: MoviesViewModel
    private var moviesAdapter: GenericAdapter<Movie>? = null
    private var searchAdapter: GenericAdapter<MovieSearch>? = null
    private var movieList:MutableList<Movie> = ArrayList()
    private val movieListSearch: MutableList<MovieSearch> = ArrayList()
    private var querySearch: String = ""
    private var handler: Handler = Handler()
    private var runnable: Runnable ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)
        loadMovies()
    }

    override fun layoutId(): Int = R.layout.fragment_movies

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.showHideProgress.reObserve(this, Observer { showHideProgressBar(it) })
        viewModel.mostPopularMovies.reObserve(this, Observer { showPopularMovies(it) })
        viewModel.moviesSearchShow.reObserve(this, Observer { showSearchedMovies(it) })
        viewModel.errorConnection.reObserve(this, Observer { showErrorHandler(it) })
        viewModel.errorService.reObserve(this, Observer { showToast(getString(R.string.errorService), Toast.LENGTH_SHORT) })

        setAdapter()
    }

    private fun showSearchedMovies(list: List<MovieSearch>?) {
        list?.let {
            movieListSearch.addAll(list.toMutableList())
            searchAdapter?.itemThreshHold = viewModel.qtyItemsToShow
            searchAdapter?.addAllItems(movieListSearch)
        }
    }

    private fun setUpSearchView(item: MenuItem) {
        val searchView = item.actionView as SearchView
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                setAdapter()
                moviesAdapter?.addAllItems(movieList)
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // Do something when expanded
                moviesAdapter?.addAllItems(emptyList<Movie>().toMutableList())
                setSearchAdapter()
//                viewModel.increaseSearchPageCounter()
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Waiting some seconds to get user input in order to avoid unnecessary queries
                handler.removeCallbacks(runnable)
                movieListSearch.clear()
                runnable = Runnable { callSearch(newText) }
                handler.postDelayed(runnable, 500)
                return true
            }

            private fun callSearch(query: String) {
                // Called every time a letter change on searchView
                viewModel.increaseSearchPageCounter()
                viewModel.searchMovies(query)
                querySearch = query
                Log.d("TAGSEARCH", "Text to be search: $query")
            }

        })
    }

    private fun setSearchAdapter(){
        view?.let { view ->
            searchAdapter = RVAdapterFactory.createAdapter()
            searchAdapter?.let { adapter ->
                val layoutManager = LinearLayoutManager(context)
                view.moviesRecycler.layoutManager = layoutManager
                view.moviesRecycler.adapter = searchAdapter
                adapter.onLastItemLoaded = { loadSearchMovies() }
            }
        }
    }

    private fun loadSearchMovies() {
//        viewModel.increaseSearchPageCounter()
        viewModel.searchMovies(querySearch)
    }

    private fun loadMovies(){
        viewModel.getPopularMovies()
    }

    private fun showErrorHandler(isError: Boolean?) {
        isError?.let {
            showSnackWithAction(R.string.no_internet_connection, R.string.refresh, ::loadMovies)
        }
    }

    private fun setAdapter() {
        view?.let { view ->
            moviesAdapter = RVAdapterFactory.createAdapter()
            moviesAdapter?.let { adapter ->
                adapter.onClickAction = {callClickAction(it)}
                val layoutManager = GridLayoutManager(context, 2)
                view.moviesRecycler.layoutManager = layoutManager
                view.moviesRecycler.adapter = moviesAdapter
                adapter.onLastItemLoaded = { loadMovies() }
            }
        }
    }

    private fun callClickAction(movie: Movie) {
        showToast(getString(R.string.item_clicked, movie.title), Toast.LENGTH_SHORT)
    }

    private fun showPopularMovies(list: List<Movie>?) {
        list?.let {
            movieList.addAll(list.toMutableList())
            moviesAdapter?.itemThreshHold = viewModel.qtyItemsToShow
            moviesAdapter?.addAllItems(movieList)
        }
    }

    private fun showHideProgressBar(showPB: Boolean?) {
        showPB?.let {
            if (it) showProgress()
            else hideProgress()
        }
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.let {
            inflater.inflate(R.menu.main_home_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            action_search -> { setUpSearchView(item)
                true }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}