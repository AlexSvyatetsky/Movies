package com.sa1nt.movies.di.module

import android.app.Application
import android.content.Context
import com.sa1nt.movies.data.local.dataSource.MoviePrefrence
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {


    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }


    @Provides
    @Singleton
    internal fun provideAppPrefrence(mContext: Context): MoviePrefrence {
        return MoviePrefrence(mContext)
    }

}