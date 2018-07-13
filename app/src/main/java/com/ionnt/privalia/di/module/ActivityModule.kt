package com.ionnt.privalia.di.module

import com.ionnt.privalia.di.annotations.ActivityScope
import com.ionnt.privalia.ui.movie.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Martin De Girolamo on 09/06/2018.
 */
@Module
abstract class ActivityModule{
    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    @ActivityScope
    abstract fun contributeMainActivity(): MainActivity
}