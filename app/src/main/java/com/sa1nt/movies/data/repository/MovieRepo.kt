package com.sa1nt.movies.data.repository

import com.sa1nt.movies.data.remote.TrailerRemote
import com.sa1nt.movies.domain.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepo {
    fun getMovies(): Flow<List<MovieEntity>?>
    suspend fun fetchMovieTrailers(movieId: Int): ArrayList<TrailerRemote>?
    fun isMovieLiked(id: Int): Boolean
    fun changeLikeState(id: Int, newLikeState: Boolean): Int
}