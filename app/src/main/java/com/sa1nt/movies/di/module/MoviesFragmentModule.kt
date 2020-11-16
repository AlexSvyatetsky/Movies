package com.sa1nt.movies.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sa1nt.movies.data.repository.MovieRepoImp
import com.sa1nt.movies.presentation.commons.GridSpacingItemDecoration
import com.sa1nt.movies.presentation.commons.ViewModelProviderFactory
import com.sa1nt.movies.presentation.movies.MovieAdapter
import com.sa1nt.movies.presentation.movies.MoviesFragment
import com.sa1nt.movies.presentation.movies.MoviesViewModel
import dagger.Module
import dagger.Provides

@Module
class MoviesFragmentModule {


    @Provides
    internal fun provideMainFragmentViewModel(movieRepoImp: MovieRepoImp): MoviesViewModel {
        return MoviesViewModel(movieRepoImp)
    }

    @Provides
    internal fun provideGridLayoutManager(fragment: MoviesFragment): GridLayoutManager {
        return GridLayoutManager((fragment.activity as Context?)!!, 2)
    }

    @Provides
    internal fun provideGridSpacingItemDecoration(): GridSpacingItemDecoration {
        return GridSpacingItemDecoration(2, 5, true)
    }

    @Provides
    internal fun provideMovieAdapter(context: Context): MovieAdapter {
        return MovieAdapter(ArrayList(),context)
    }


    @Provides
    internal fun mainFragmentViewModelProvider(moviesViewModel: MoviesViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(moviesViewModel)
    }

}