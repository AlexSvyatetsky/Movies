package com.sa1nt.movies.di.module

import com.sa1nt.movies.presentation.commons.AppConstants
import com.sa1nt.movies.presentation.commons.AppConstants.Companion.BASE_URL_KEY
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class UrlModule {
    @Provides
    @Singleton
    @Named(BASE_URL_KEY)
    fun provideBaseUrl(): String {
        return AppConstants.BASE_URL
    }
}