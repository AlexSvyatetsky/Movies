package com.sa1nt.movies.presentation.detail

import androidx.lifecycle.viewModelScope
import com.sa1nt.movies.data.repository.MovieRepo
import com.sa1nt.movies.domain.MovieEntity
import com.sa1nt.movies.presentation.base.BaseViewModel
import com.sa1nt.movies.presentation.commons.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val movieRepo: MovieRepo,
                                          private val dispatcher: CoroutineDispatcher = CoroutineDispatcher()
) : BaseViewModel() {

    fun getLikeState(movieId: Int) {
        viewModelScope.launch(dispatcher.IO) {
            val likeState = movieRepo.isMovieLiked(movieId)
            withContext(dispatcher.Main) { onSuccess(DetailViewState.LikeState(likeState)) }
        }
    }

    fun fetchMovieTrailers(movieId: Int) {
        viewModelScope.launch(dispatcher.IO) {
            val trailerList = movieRepo.fetchMovieTrailers(movieId)
            withContext(dispatcher.Main) {
                if (!trailerList.isNullOrEmpty())
                    onSuccess(DetailViewState.TrailersFetched(trailerList))
                else
                    onError("No trailers found")
            }
        }
    }

    fun updateLikeStatus(movie: MovieEntity) {
        viewModelScope.launch(dispatcher.IO) {
            val newLikeState = movie.isFav.not()
            movieRepo.changeLikeState(movie.id, newLikeState).also { movie.isFav = newLikeState }
            withContext(dispatcher.Main) {
                onSuccess(DetailViewState.LikeState(newLikeState))
            }
        }
    }


}

