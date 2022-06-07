package com.samsdk.jetpackpaging.paging

interface Pagination<Key, Item> {
    suspend fun loadNextItem()
    fun reset()
}