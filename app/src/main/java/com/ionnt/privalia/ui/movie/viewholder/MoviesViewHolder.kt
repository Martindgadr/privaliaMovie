package com.ionnt.privalia.ui.movie.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ionnt.privalia.R
import com.ionnt.privalia.commons.extensions.loadFromUrl
import com.ionnt.privalia.commons.recyclerview.interfaces.DataBinder
import com.ionnt.privalia.ui.movie.entities.Movie

/**
 * Created by Martin De Girolamo on 10/06/2018.
 */

class MoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), DataBinder<Movie> {
    private val moviePicture = itemView.findViewById<ImageView>(R.id.imageView)
    private val movieName = itemView.findViewById<TextView>(R.id.movieNameText)
    private val movieYear = itemView.findViewById<TextView>(R.id.movieYearText)

    override fun bindData(itemData: Movie) {
        movieName.text = itemData.title
        movieYear.text = itemData.year.toString()
        moviePicture.loadFromUrl(itemData.photoUrl)
    }
}
