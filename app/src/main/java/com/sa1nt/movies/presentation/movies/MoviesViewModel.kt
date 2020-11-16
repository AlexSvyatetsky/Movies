package com.sa1nt.movies.presentation.movies

import androidx.lifecycle.viewModelScope
import com.sa1nt.movies.data.repository.MovieRepo
import com.sa1nt.movies.presentation.base.BaseViewModel
import com.sa1nt.movies.presentation.commons.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesViewModel @Inject constructor(private val movieRepo: MovieRepo,
                                          private val dispatcher: CoroutineDispatcher = CoroutineDispatcher()
) : BaseViewModel() {


    fun getMovies() {
        viewModelScope.launch(dispatcher.IO) {
            movieRepo.getMovies().collect {
                withContext(dispatcher.Main) {
                    if (!it.isNullOrEmpty())
                        onSuccess(it)
                    else
                        onError("No movies found")
                }
            }
        }
    }


}