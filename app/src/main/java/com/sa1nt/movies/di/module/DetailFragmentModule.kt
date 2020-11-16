package com.sa1nt.movies.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sa1nt.movies.data.repository.MovieRepoImp
import com.sa1nt.movies.presentation.commons.ViewModelProviderFactory
import com.sa1nt.movies.presentation.detail.DetailFragment
import com.sa1nt.movies.presentation.detail.DetailViewModel
import com.sa1nt.movies.presentation.detail.TrailerAdapter
import dagger.Module
import dagger.Provides

@Module
class DetailFragmentModule {

    @Provides
    internal fun provideDetailFragmentViewModel(movieRepoImpl: MovieRepoImp): DetailViewModel {
        return DetailViewModel(movieRepoImpl)
    }

    @Provides
    internal fun provideLinearLayoutManager(fragment: DetailFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }

    @Provides
    internal fun provideTrailerAdapter(context: Context): TrailerAdapter {
        return TrailerAdapter(ArrayList(), context)
    }

    @Provides
    internal fun detailFragmentViewModelProvider(mainViewModel: DetailViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(mainViewModel)
    }

}