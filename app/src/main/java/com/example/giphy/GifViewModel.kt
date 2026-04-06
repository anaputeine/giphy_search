package com.example.giphy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class GifViewModel(
    //for testing
    private val service: GifApi = GifClient.service, private val dispatcher: kotlinx.coroutines.CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    //pagination
    private var currentOffset = 0
    private val PAGE_SIZE = 20
    private var currentQuery = ""

    //loading indicator
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    //error handling
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    fun clearError() {
        _error.value = null
    }

    //basic search
    private val API_KEY = "UVkGDKGg6Hywlis2zQK2ERPWesm3GSoq"
    private val _gifs = MutableStateFlow<List<GifObject>>(emptyList())
    val gifs: StateFlow<List<GifObject>> = _gifs


    // search for GIFs
    fun searchGifs(query: String, isNextPage: Boolean = false) {
        if (_isLoading.value) return


        // new search
        if (!isNextPage || query != currentQuery) {
            currentOffset = 0
            currentQuery = query
        }

        // query is blank
        if (currentQuery.isBlank()) {
            _isLoading.value = false
            _gifs.value = emptyList()
            return
        }

        _error.value = null
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = GifClient.service.searchGifs(
                    API_KEY,
                    currentQuery,
                    limit = PAGE_SIZE,
                    offset = currentOffset
                )

                _gifs.value = if (isNextPage) _gifs.value + response.data
                else response.data

                currentOffset += PAGE_SIZE
            } catch (e: Exception) {
                e.printStackTrace()

                _error.value = when (e) {
                    is java.io.IOException -> "No internet connection"
                    else -> "Something went wrong"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}