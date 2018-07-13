package com.ionnt.privalia.ui.movie.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ionnt.privalia.R
import com.ionnt.privalia.commons.extensions.loadFromUrl
import com.ionnt.privalia.commons.recyclerview.interfaces.DataBinder
import com.ionnt.privalia.ui.movie.entities.MovieSearch

/**
 * Created by Martin De Girolamo on 25/06/2018.
 */
class MoviesSearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), DataBinder<MovieSearch> {
    private val moviePicture = itemView.findViewById<ImageView>(R.id.imageView)
    private val movieName = itemView.findViewById<TextView>(R.id.movieNameText)
    private val movieYear = itemView.findViewById<TextView>(R.id.movieYearText)
    private val movieOverview = itemView.findViewById<TextView>(R.id.movieOverviewText)

    override fun bindData(itemData: MovieSearch) {
        movieName.text = itemData.title
        movieYear.text = itemData.year.toString()
        itemData.photoUrl?.let { moviePicture.loadFromUrl(it) }
        itemData.overview.let { movieOverview.text = it }
    }
}