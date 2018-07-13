package com.ionnt.privalia.commons.recyclerview.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ionnt.privalia.commons.recyclerview.interfaces.DataBinder

/**
 * Created by Martin De Girolamo on 09/06/2018.
 *
 * This Generic Adapter, was build in order to avoid boilerplate code, all the time recyclerview
 * adapters run the same form, using this and 2 factory classes ViewHolderFactory, RVAdapterFactory
 * is easy to implement logic for multiple recycler views, the Idea is associate a POJO Class and
 * a layout in order to get generic function of this.
 */

abstract class GenericAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listItems: MutableList<T>
    var onClickAction: ((T) -> Unit)? = null
    var onLastItemLoaded: (() -> Unit)? = null
    var itemThreshHold: Int = 1 // total items to be showed in recycler using pagination

    init {
        listItems = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
                , viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        @Suppress("UNCHECKED_CAST")
        (holder as DataBinder<T>).bindData(listItems[position])
        onClickAction?.let {
            holder.itemView.setOnClickListener{it(listItems[position])}
        }

        onLastItemLoaded?.let { if (position == itemCount - 1) it.invoke() }

    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position, listItems[position])
    }

    fun addAllItems(itemList: MutableList<T>) {
        listItems = itemList
        notifyDataSetChanged()
    }

    fun addItem(singleItem: T){
        listItems.add(singleItem)
        notifyDataSetChanged()
    }

    fun appendList(itemList: MutableList<T>) {
        listItems.addAll(itemList)
        notifyDataSetChanged()
    }

    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder

}