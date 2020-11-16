package com.sa1nt.movies.data.local.dataSource

import androidx.room.*
import com.sa1nt.movies.data.local.MovieLocal
import javax.inject.Singleton

@Singleton
@Database(entities = [(MovieLocal::class)], version = 4, exportSchema = false)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieLocal>)

    @Query("SELECT * FROM movies order by popularity DESC")
    fun fetchAllMovies(): List<MovieLocal>

    @Query("SELECT is_favourite FROM movies WHERE id = :movieId ")
    fun isMovieLiked(movieId: Int): List<Int?>

    @Query("update movies set is_favourite = 1 where id = :movieId")
    fun setMovieLiked(movieId: Int): Int

    @Query("update movies set is_favourite = 0  where id = :movieId ")
    fun setMovieUnLiked(movieId: Int): Int

}
