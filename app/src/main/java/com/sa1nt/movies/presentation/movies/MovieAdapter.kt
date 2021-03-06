package com.sa1nt.movies.presentation.movies

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sa1nt.movies.R
import com.sa1nt.movies.domain.MovieEntity
import javax.inject.Inject

class MovieAdapter @Inject constructor(private val mMoviesList: MutableList<MovieEntity>, val mContext: Context) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    val mLayoutInflater = LayoutInflater.from(mContext);
    private lateinit var listener: callback

    override fun getItemCount(): Int {
        return mMoviesList.size
    }

    fun setListener(mCallback: callback) {
        listener = mCallback
    }

    fun addItems(mList: ArrayList<MovieEntity>?) {
        if (mList != null) {
            clearItems()
            mMoviesList.addAll(mList)
            notifyDataSetChanged()
        }
    }


    private fun clearItems() {
        mMoviesList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = mLayoutInflater.inflate(R.layout.item_movie_view, parent, false)
        return MovieViewHolder(view)
    }

    // stores and recycles views as they are scrolled off screen
    inner class MovieViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myImgView: ImageView = itemView.findViewById(R.id.movieImg) as ImageView

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val moviePoster = mMoviesList[position].poster_path
        if (moviePoster != null)
            Glide.with(mContext)
                    .load(Uri.parse("http://image.tmdb.org/t/p/w185$moviePoster"))
                    .into(holder.myImgView)
        Log.v("Img = ", "http://image.tmdb.org/t/p/w185$moviePoster")
        holder.myImgView.setOnClickListener { listener.onItemClick(mMoviesList[position]) }
    }

    interface callback {
        fun onItemClick(movieRemote: MovieEntity)
    }

}
