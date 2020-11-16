package com.sa1nt.movies.domain

import com.sa1nt.movies.data.local.MovieLocal
import com.sa1nt.movies.data.remote.MovieRemote

interface MovieMapper {
     fun mapFromLocal(from: MovieLocal): MovieEntity
     fun mapFromRemoteToLocal(from: MovieRemote, isFav: Int): MovieLocal
}