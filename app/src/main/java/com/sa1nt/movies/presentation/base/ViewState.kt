package com.sa1nt.movies.presentation.base

sealed class ViewState {
    class HasData<T : Any>(val data: T) : ViewState()
    class HasError(val error: String) : ViewState()
}

