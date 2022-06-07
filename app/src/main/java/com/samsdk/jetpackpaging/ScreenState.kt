package com.samsdk.jetpackpaging

import com.samsdk.jetpackpaging.model.Data

data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<Data> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
