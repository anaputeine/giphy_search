package com.example.giphy

data class GifObject(
    val id: String,
    val title: String,
    val images: Images,
    val rating: String? = null,
    val import_datetime: String? = null,
    val trending_datetime: String? = null

)

data class Images(
    val fixed_height: GifImage
)

data class GifImage(
    val url: String
)


data class GiphyResponse(
    val data: List<GifObject>
)