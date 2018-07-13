package com.ionnt.privalia.commons.recyclerview.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ionnt.privalia.R
import com.ionnt.privalia.commons.recyclerview.interfaces.DataBinder

/**
 * Created by Martin De Girolamo on 10/06/2018.
 */

class HeaderTitleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), DataBinder<String> {
    private val itemTitleText = itemView.findViewById<TextView>(R.id.itemTextTitle)

    override fun bindData(itemData: String) {
        itemTitleText.text = itemData
    }
}