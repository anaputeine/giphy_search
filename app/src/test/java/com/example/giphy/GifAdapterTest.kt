package com.example.giphy

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GifAdapterTest {

    private lateinit var adapter: GifAdapter
    private val noOpClickHandler: (GifObject) -> Unit = {}

    @Before
    fun setup() {
        adapter = GifAdapter(emptyList(), noOpClickHandler)
    }

    @Test
    fun `Adapter starts with zero items`() {
        assertEquals(0, adapter.gifs.size)
    }

    @Test
    fun `Adapter item count matches gif list size`() {
        val gifs = listOf(
            GifObject("1", "Cat", Images(GifImage("https://cat.gif"))),
            GifObject("2", "Dog", Images(GifImage("https://dog.gif"))),
            GifObject("3", "Frog", Images(GifImage("https://frog.gif")))
        )

        adapter.updateGifs(gifs)

        assertEquals(3, adapter.gifs.size)
    }

    @Test
    fun `Updating gifs replaces old list`() {
        val firstBatch = listOf(
            GifObject("1", "Cat", Images(GifImage("https://cat.gif")))
        )
        adapter.updateGifs(firstBatch)

        val secondBatch = listOf(
            GifObject("2", "Dog", Images(GifImage("https://dog.gif"))),
            GifObject("3", "Frog", Images(GifImage("https://frog.gif")))
        )
        adapter.updateGifs(secondBatch)

        assertEquals(2, adapter.gifs.size)
    }

}