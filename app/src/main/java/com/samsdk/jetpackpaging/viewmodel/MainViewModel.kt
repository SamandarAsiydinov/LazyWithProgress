package com.samsdk.jetpackpaging.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samsdk.jetpackpaging.ScreenState
import com.samsdk.jetpackpaging.paging.DefaultPagination
import com.samsdk.jetpackpaging.repository.DataRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = DataRepository()

    var state by mutableStateOf(ScreenState())
    private val pagination = DefaultPagination(
        initialKey = state.page,
        onLoadUploaded = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            repository.getItems(nextPage, 30)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            pagination.loadNextItem()
        }
    }
}