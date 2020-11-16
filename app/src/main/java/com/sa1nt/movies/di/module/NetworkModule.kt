package com.sa1nt.movies.di.module

import android.content.Context
import com.sa1nt.movies.data.networkConfig.NetworkConnectionInterceptor
import com.sa1nt.movies.data.remote.dataSource.MovieApi
import com.sa1nt.movies.data.remote.dataSource.TrailerApi
import com.sa1nt.movies.presentation.commons.AppConstants.Companion.BASE_URL_KEY
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideNetworkConnectionInterceptor(mContext: Context): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(mContext)
    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor
                                     , httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(networkConnectionInterceptor)
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(@Named(BASE_URL_KEY) baseUrl: String,okHttpClient: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .baseUrl(baseUrl)
                .build()
    }


    @Provides
    @Singleton
    internal fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideTrailerApi(retrofit: Retrofit): TrailerApi {
        return retrofit.create(TrailerApi::class.java)
    }

}