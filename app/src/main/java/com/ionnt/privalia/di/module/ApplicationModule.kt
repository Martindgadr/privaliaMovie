package com.ionnt.privalia.di.module

import com.ionnt.PrivaliaApplication
import com.ionnt.privalia.ui.movie.repositories.MoviesRepository
import com.ionnt.privalia.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Martin De Girolamo on 09/06/2018.
 */
@Module
class ApplicationModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(createClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: PrivaliaApplication) = application.applicationContext

    @Provides
    @Singleton
    fun provideMoviesRepository(retrofit: Retrofit): MoviesRepository = MoviesRepository(retrofit)


    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
//        if (BuildConfig.DEBUG) {
//            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
//            okHttpClientBuilder.addInterceptor(loggingInterceptor)
//        }

        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

        return okHttpClientBuilder.build()
    }
}