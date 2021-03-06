package com.sa1nt.movies.data.repository

import ahmed.atwa.popularmovies.data.repository.BaseRepoImp
import com.sa1nt.movies.data.local.MovieLocal
import com.sa1nt.movies.data.local.dataSource.MovieDao
import com.sa1nt.movies.data.remote.MovieRemote
import com.sa1nt.movies.data.remote.TrailerRemote
import com.sa1nt.movies.data.remote.dataSource.MovieApi
import com.sa1nt.movies.data.remote.dataSource.TrailerApi
import com.sa1nt.movies.domain.MovieEntity
import com.sa1nt.movies.domain.MovieMapper
import com.sa1nt.movies.presentation.commons.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepoImp @Inject constructor(
    private val movieDao: MovieDao,
    private val movieApi: MovieApi,
    private val trailerApi: TrailerApi,
    private val movieMapper: MovieMapper
) : BaseRepoImp(), MovieRepo {


    override fun getMovies(): Flow<List<MovieEntity>?> = flow {
        val localData = fetchMoviesLocal().map { movieMapper.mapFromLocal(it) }.toList()
        if (!localData.isNullOrEmpty())
            emit(localData)

        val syncedData = syncFavWithDb(fetchMoviesRemote())?.map { movieMapper.mapFromLocal(it) }?.toList()
        emit(syncedData)
    }



    private fun fetchMoviesLocal(): List<MovieLocal> = movieDao.fetchAllMovies()

    private suspend fun fetchMoviesRemote(): ArrayList<MovieRemote>? {
        val data = safeApiCall({ movieApi.getMostPopular(AppConstants.API_KEY) }, "fetching movies")
        return if (data != null) data.results as ArrayList<MovieRemote> else null
    }

    private fun syncFavWithDb(movieRemotes: ArrayList<MovieRemote>?): ArrayList<MovieLocal>? {
        val syncedMovies = movieRemotes?.map { movieMapper.mapFromRemoteToLocal(it, getLikeState(it.id)) }?.toList()
        syncedMovies?.let { movieDao.insertAll(it) }
        return syncedMovies as ArrayList<MovieLocal>?
    }

    override suspend fun fetchMovieTrailers(movieId: Int): ArrayList<TrailerRemote>? {
        val data = safeApiCall({ trailerApi.getMovieTrailer(movieId, AppConstants.API_KEY) }, "Error fetching Trailers")
        return if (data != null) data.results as ArrayList<TrailerRemote> else null
    }

    override fun isMovieLiked(id: Int): Boolean {
        val result = movieDao.isMovieLiked(id)
        return if (result.isNotEmpty() && result[0] != null) result[0] == 1 else false
    }

    private fun getLikeState(id: Int): Int {
        return if (isMovieLiked(id)) 1 else 0
    }

    override fun changeLikeState(id: Int, newLikeState: Boolean) = if (newLikeState) movieDao.setMovieLiked(id) else movieDao.setMovieUnLiked(id)

}