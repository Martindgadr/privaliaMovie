package com.ionnt.privalia.di

import com.ionnt.PrivaliaApplication
import com.ionnt.privalia.di.module.ActivityModule
import com.ionnt.privalia.di.module.ApplicationModule
import com.ionnt.privalia.di.module.FragmentModule
import com.ionnt.privalia.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Martin De Girolamo on 09/06/2018.
 */

@Singleton
@Component(modules = [(ApplicationModule::class), (ViewModelModule::class), (ActivityModule::class),
    (FragmentModule::class), (AndroidInjectionModule::class)])
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: PrivaliaApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: PrivaliaApplication)
}