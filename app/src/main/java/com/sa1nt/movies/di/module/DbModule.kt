package com.sa1nt.movies.di.module

import android.content.Context
import androidx.room.Room
import com.sa1nt.movies.data.local.dataSource.MovieDao
import com.sa1nt.movies.data.local.dataSource.MovieDb
import com.sa1nt.movies.presentation.commons.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule{

    @Provides
    @Singleton
    internal fun provideMovieDb(context: Context): MovieDb {
        return Room.databaseBuilder(context, MovieDb::class.java, AppConstants.DB_NAME).fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    internal fun provideMovieDao(context: Context): MovieDao {
        return provideMovieDb(context).movieDao()
    }
}