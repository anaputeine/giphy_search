package com.example.giphy

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    //Instant search
    private var searchJob: Job? = null

    private val viewModel: GifViewModel by viewModels()
    private lateinit var adapter: GifAdapter

    private var currentQuery = ""

    // Save query if rotation
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("query", currentQuery)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val searchView = findViewById<SearchView>(R.id.searchView)

        // restore query and set if rotation
        currentQuery = savedInstanceState?.getString("query") ?: ""
        searchView.setQuery(currentQuery, false)
        searchView.isIconified = false
        searchView.clearFocus()


        adapter = GifAdapter(emptyList())
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        // observe gifs from viewmodel
        lifecycleScope.launch {
            viewModel.gifs.collect { gifs ->
                adapter.updateGifs(gifs)
            }
        }


        // Search listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText ?: ""


                searchJob?.cancel()


                searchJob = lifecycleScope.launch {
                    delay(500)
                    if (query.isNotBlank()) {
                        currentQuery = query
                        viewModel.searchGifs(currentQuery)
                    } else {
                        currentQuery = ""
                        viewModel.searchGifs("")
                    }
                }
                return true
            }
        })

        //Scroll listener
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy <= 0) return

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItem + 5 >= totalItemCount) {
                    viewModel.searchGifs(currentQuery, isNextPage = true)
                }
            }
        })
    }
}