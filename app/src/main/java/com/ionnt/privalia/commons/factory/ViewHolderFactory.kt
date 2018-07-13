package com.ionnt.privalia.commons.factory

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ionnt.privalia.R
import com.ionnt.privalia.commons.recyclerview.viewholder.HeaderTitleViewHolder
import com.ionnt.privalia.ui.movie.viewholder.MoviesSearchViewHolder
import com.ionnt.privalia.ui.movie.viewholder.MoviesViewHolder

/**
 * Created by Martin De Girolamo on 09/06/2018.
 */
class ViewHolderFactory {
    companion object {
        fun create(view: View, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                R.layout.movie_item -> MoviesViewHolder(view)
                R.layout.movie_item_search -> MoviesSearchViewHolder(view)
                else -> HeaderTitleViewHolder(view)
            }
        }
    }
}