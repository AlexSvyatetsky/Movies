package com.sa1nt.movies.di.builder

import com.sa1nt.movies.di.module.DetailFragmentProvider
import com.sa1nt.movies.di.module.MainActivityModule
import com.sa1nt.movies.di.module.MoviesFragmentProvider
import com.sa1nt.movies.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class), (MoviesFragmentProvider::class), (DetailFragmentProvider::class)])
    internal abstract fun bindMainActivity(): MainActivity

}