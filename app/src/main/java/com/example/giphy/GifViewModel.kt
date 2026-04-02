package com.example.giphy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Manage GIF data
class GifViewModel : ViewModel() {
    private val API_KEY = "UVkGDKGg6Hywlis2zQK2ERPWesm3GSoq"
    private val _gifs = MutableStateFlow<List<GifObject>>(emptyList())
    val gifs: StateFlow<List<GifObject>> = _gifs

    // Load trending GIFs
    fun loadTrending() {
        viewModelScope.launch {
            try {
                val response = GifClient.service.getTrendingGifs(API_KEY)
                _gifs.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Search for GIFs
    fun searchGifs(query: String) {
        if (query.isBlank()) {
            loadTrending()
            return
        }
        viewModelScope.launch {
            try {
                val response = GifClient.service.searchGifs(API_KEY, query)
                _gifs.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}