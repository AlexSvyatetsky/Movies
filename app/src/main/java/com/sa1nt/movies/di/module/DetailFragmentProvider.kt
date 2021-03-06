package com.sa1nt.movies.di.module

import com.sa1nt.movies.presentation.detail.DetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class DetailFragmentProvider {

    @ContributesAndroidInjector(modules =[(DetailFragmentModule::class)])
    internal abstract fun provideDetailFragmentFactory(): DetailFragment

}