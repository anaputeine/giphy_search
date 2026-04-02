package com.example.giphy

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // decoding GIFs depending on Android version
        val imageLoader = ImageLoader.Builder(this)
            .components {
                if (android.os.Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory()) // Android 9+
                } else {
                    add(GifDecoder.Factory())          // older Android
                }
            }
            .build()

        Coil.setImageLoader(imageLoader)
    }
}