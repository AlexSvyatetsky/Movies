package com.sa1nt.movies.presentation.movies

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.sa1nt.movies.R
import com.sa1nt.movies.domain.MovieEntity
import com.sa1nt.movies.presentation.base.BaseFragment
import com.sa1nt.movies.presentation.commons.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MoviesFragment : BaseFragment<MoviesViewModel>(), MovieAdapter.callback {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mGridLayoutManager: GridLayoutManager

    @Inject
    lateinit var mGridSpacingItemDecoration: GridSpacingItemDecoration

    @Inject
    lateinit var mMovieAdapter: MovieAdapter

    lateinit var mListener: MainFragmentListener


    override fun getLayoutId(): Int = R.layout.fragment_movies
    override fun getViewModel(): MoviesViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        MoviesViewModel::class.java)
    override fun getLifeCycleOwner(): LifecycleOwner = this

    override fun initUI() {
        mGridLayoutManager.reverseLayout = false
        mGridLayoutManager.isItemPrefetchEnabled = false
        moviesRecycler.setHasFixedSize(true)
        moviesRecycler.layoutManager = mGridLayoutManager
        moviesRecycler.addItemDecoration(mGridSpacingItemDecoration)
        moviesRecycler.itemAnimator = DefaultItemAnimator()
        moviesRecycler.adapter = mMovieAdapter
        mMovieAdapter.setListener(this)
        showLoading()
        getViewModel().getMovies()
    }

    override fun onSuccess(data: Any) {
        if (data is ArrayList<*>) mMovieAdapter.addItems(data as ArrayList<MovieEntity>?)
    }

    override fun onFailure(error: String) {
        showMessage(error)
    }

    override fun onItemClick(movieRemote: MovieEntity) {
        mListener.onMovieSelected(movieRemote)
    }

    interface MainFragmentListener {
        fun onMovieSelected(movieRemote: MovieEntity)
    }


}