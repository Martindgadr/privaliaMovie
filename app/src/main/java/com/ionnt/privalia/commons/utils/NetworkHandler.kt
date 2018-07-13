package com.ionnt.privalia.commons.utils

import android.content.Context
import com.ionnt.privalia.commons.extensions.networkState
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Martin De Girolamo on 16/06/2018.
 */
@Singleton
class NetworkHandler @Inject constructor(private val context: Context) {
    val isConnected get() = context.networkState?.isConnectedOrConnecting
}