package com.sa1nt.movies.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sa1nt.movies.presentation.commons.toSingleEvent

abstract class BaseViewModel() : ViewModel() {

    private val mUiState = MutableLiveData<ViewState>()
    open val uiState: LiveData<ViewState> = mUiState.toSingleEvent()

    fun <T : Any> onSuccess(result: T) {
        mUiState.value = ViewState.HasData(result)
    }

    protected fun onError(error: String) {
        mUiState.value = ViewState.HasError(error)
    }


}