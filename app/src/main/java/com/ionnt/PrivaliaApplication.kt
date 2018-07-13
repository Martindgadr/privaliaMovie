package com.ionnt

import android.app.Activity
import android.app.Application
import com.ionnt.privalia.di.ApplicationComponent
import com.ionnt.privalia.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Martin De Girolamo on 09/06/2018.
 */

class PrivaliaApplication: Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder().application(this).build()
        applicationComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}