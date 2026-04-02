package com.example.giphy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Manage GIF data
class GifViewModel : ViewModel() {
    //Pagination
    private var currentOffset = 0
    private val PAGE_SIZE = 20
    private var isLoading = false
    private var currentQuery = ""

    //Basic search
    private val API_KEY = "UVkGDKGg6Hywlis2zQK2ERPWesm3GSoq"
    private val _gifs = MutableStateFlow<List<GifObject>>(emptyList())
    val gifs: StateFlow<List<GifObject>> = _gifs


    // Search for GIFs
    fun searchGifs(query: String, isNextPage: Boolean = false) {
        if (isLoading) return
        isLoading = true

        // new search
        if (!isNextPage || query != currentQuery) {
            currentOffset = 0
            currentQuery = query
        }

        // query is blank
        if (currentQuery.isBlank()) {
            isLoading = false
            _gifs.value = emptyList()
            return
        }

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
            } finally {
                isLoading = false
            }
        }
    }
}