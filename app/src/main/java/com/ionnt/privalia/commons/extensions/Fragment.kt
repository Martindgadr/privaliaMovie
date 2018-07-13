package com.ionnt.privalia.commons.extensions

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.ionnt.privalia.commons.base.BaseActivity
import com.ionnt.privalia.commons.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Martin De Girolamo on 17/06/2018.
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragment_container
val BaseFragment.appContext: Context get() = activity?.applicationContext!!