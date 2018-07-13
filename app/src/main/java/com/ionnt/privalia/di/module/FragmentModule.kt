package com.ionnt.privalia.di.module

import com.ionnt.privalia.di.annotations.FragmentScope
import com.ionnt.privalia.ui.movie.ui.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Martin De Girolamo on 09/06/2018.
 */
@Module
abstract class FragmentModule{
    @ContributesAndroidInjector
    @FragmentScope
    abstract
    fun contributeMainFragment(): MainFragment
}