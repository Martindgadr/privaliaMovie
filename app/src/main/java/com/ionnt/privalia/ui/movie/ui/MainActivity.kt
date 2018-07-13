package com.ionnt.privalia.ui.movie.ui

import android.os.Bundle
import com.ionnt.privalia.R
import com.ionnt.privalia.commons.base.BaseActivity
import com.ionnt.privalia.commons.extensions.replaceFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initializeFragment() {
        replaceFragment(false, R.id.fragment_container, MainFragment.newInstance(),
                Bundle(), true)
    }
}
