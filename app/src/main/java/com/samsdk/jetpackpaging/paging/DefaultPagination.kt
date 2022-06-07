package com.samsdk.jetpackpaging.paging

class DefaultPagination<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUploaded: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend(items: List<Item>, newKey: Key) -> Unit
) : Pagination<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItem() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = !isMakingRequest
        onLoadUploaded(true)
        val result = onRequest(currentKey)
        isMakingRequest = !isMakingRequest
        val items = result.getOrElse {
            onError(it)
            onLoadUploaded(false)
            return
        }
        currentKey = getNextKey(items)
        onSuccess(items, currentKey)
        onLoadUploaded(false)
    }

    override fun reset() {
        currentKey = initialKey
    }
}