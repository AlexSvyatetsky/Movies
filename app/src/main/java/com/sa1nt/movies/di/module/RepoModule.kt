package com.sa1nt.movies.di.module


import com.sa1nt.movies.data.local.dataSource.MovieDao
import com.sa1nt.movies.data.remote.dataSource.MovieApi
import com.sa1nt.movies.data.remote.dataSource.TrailerApi
import com.sa1nt.movies.data.repository.MovieRepo
import com.sa1nt.movies.data.repository.MovieRepoImp
import com.sa1nt.movies.domain.MovieMapper
import com.sa1nt.movies.domain.MovieMapperImp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {


    @Provides
    @Singleton
    internal fun provideMovieDataMapper(): MovieMapper {
        return MovieMapperImp()
    }

    @Provides
    @Singleton
    internal fun provideMovieRepository(movieDao: MovieDao,
                                        movieApi: MovieApi,
                                        trailerApi: TrailerApi,
                                        movieMapper: MovieMapper
    ): MovieRepo {
        return MovieRepoImp(movieDao, movieApi, trailerApi,movieMapper)
    }

}