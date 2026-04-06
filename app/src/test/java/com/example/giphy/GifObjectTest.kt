package com.example.giphy

import org.junit.Assert.*
import org.junit.Test


class GifObjectTest {

    @Test
    fun `GifObject's fields are stored correctly`() {
        val gifImage = GifImage(url = "https://example.com/frog.gif")
        val images = Images(fixed_height = gifImage)
        val gif = GifObject(id = "abc123", title = "Funny Frog", images = images)

        assertEquals("abc123", gif.id)
        assertEquals("Funny Frog", gif.title)
        assertEquals("https://example.com/frog.gif", gif.images.fixed_height.url)
    }

    @Test
    fun `GiphyResponse is a list of GifObjects`() {
        val gif1 = GifObject("1", "Cat", Images(GifImage("https://cat.gif")))
        val gif2 = GifObject("2", "Dog", Images(GifImage("https://dog.gif")))
        val response = GiphyResponse(data = listOf(gif1, gif2))


        assertEquals(2, response.data.size)
        assertEquals("Cat", response.data[0].title)
        assertEquals("Dog", response.data[1].title)
    }
}