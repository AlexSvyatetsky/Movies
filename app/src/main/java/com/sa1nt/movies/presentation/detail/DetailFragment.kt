package com.sa1nt.movies.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sa1nt.movies.R
import com.sa1nt.movies.data.remote.TrailerRemote
import com.sa1nt.movies.domain.MovieEntity
import com.sa1nt.movies.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class DetailFragment(val movie: MovieEntity) : BaseFragment<DetailViewModel>(),
    TrailerAdapter.TrailerAdapterListener {


    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager


    @Inject
    lateinit var mTrailerAdapter: TrailerAdapter

    lateinit var mListener: DetailFragmentListener

    override fun getLayoutId(): Int = R.layout.fragment_detail
    override fun getViewModel(): DetailViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        DetailViewModel::class.java)
    override fun getLifeCycleOwner(): LifecycleOwner = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTrailerAdapter.mListener = this
    }


    override fun initUI() {
        trailersRecycler.setHasFixedSize(true)
        trailersRecycler.layoutManager = mLinearLayoutManager
        trailersRecycler.itemAnimator = DefaultItemAnimator()
        trailersRecycler.adapter = mTrailerAdapter
        initMovieUi(movie)
        getViewModel().getLikeState(movie.id)
        getViewModel().fetchMovieTrailers(movie.id)
    }

    private fun initMovieUi(movie: MovieEntity) {
        title_tv.text = movie.title
        plot_tv.text = movie.overview
        release_tv.text = "Released in : ${movie.release_date}"
        ratingbar.rating = (movie.vote_average / 2).toFloat()
        count_tv.text = movie.vote_count.toString()
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w185${movie.poster_path}")
                .into(moviePoster)
        like_img.setOnClickListener { getViewModel().updateLikeStatus(movie) }
    }

    override fun onSuccess(data: Any) {
        when (data) {
            is DetailViewState.MessageRes -> showMessage(getString(data.resId))
            is DetailViewState.LikeState -> renderLikeState(data.isLiked)
            is DetailViewState.TrailersFetched<*> -> renderTrailers(data.data as ArrayList<TrailerRemote>)
        }
    }

    override fun onFailure(error: String) {
        trailers_loading.visibility = View.GONE
        showMessage(error)
    }

    private fun renderTrailers(trailers: ArrayList<TrailerRemote>) {
        trailers_loading.visibility = View.GONE
        mTrailerAdapter.addItems(trailers)
    }

    private fun renderLikeState(isLiked: Boolean) {
        if (isLiked) R.string.movie_liked else R.string.movie_unliked
        like_img.setImageResource(if (isLiked) R.drawable.like else R.drawable.dislike)
    }


    override fun onItemClick(trailerRemote: TrailerRemote) {
        mListener.onTrailerSelected(trailerRemote)
    }

    interface DetailFragmentListener {
        fun onTrailerSelected(trailerRemote: TrailerRemote)
    }


}