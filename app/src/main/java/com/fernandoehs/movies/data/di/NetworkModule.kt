package com.fernandoehs.movies.data.di

import com.fernandoehs.movies.utils.Constants
import com.fernandoehs.movies.data.network.MoviesApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesApiClient(retrofit: Retrofit): MoviesApiClient {
        return retrofit.create(MoviesApiClient::class.java)
    }
}