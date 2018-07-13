package com.ionnt.privalia.commons.factory

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ionnt.privalia.R
import com.ionnt.privalia.commons.recyclerview.adapter.GenericAdapter
import com.ionnt.privalia.ui.movie.entities.Movie
import com.ionnt.privalia.ui.movie.entities.MovieSearch

/**
 * Created by Martin De Girolamo on 09/06/2018.
 */

class RVAdapterFactory {
    companion object {
        fun <T> createAdapter(): GenericAdapter<T>? {
            return object : GenericAdapter<T>() {
                override fun getLayoutId(position: Int, obj: T): Int {
                    return when (obj) {
                        is Movie -> R.layout.movie_item
                        is MovieSearch -> R.layout.movie_item_search
                        is String -> R.layout.item_title_text
                        else -> R.layout.item_title_text
                    }
                }

                override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                    return ViewHolderFactory.create(view, viewType)
                }
            }
        }
    }
}