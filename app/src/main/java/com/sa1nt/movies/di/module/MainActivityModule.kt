package com.sa1nt.movies.di.module

import com.sa1nt.movies.data.repository.MovieRepoImp
import com.sa1nt.movies.presentation.main.MainViewModel
import dagger.Module
import dagger.Provides


@Module
class MainActivityModule {

    @Provides
    internal fun provideMainViewModel(movieRepository: MovieRepoImp): MainViewModel {
        return MainViewModel(movieRepository)
    }

}