package com.example.giphy

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import coil.load

class GifActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gif)

        //back button
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //design
        toolbar.setBackgroundColor(android.graphics.Color.BLACK)
        toolbar.setTitleTextColor(android.graphics.Color.WHITE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gifTitle = intent.getStringExtra("gif_title") ?: "GIF"
        supportActionBar?.title = gifTitle

        toolbar.navigationIcon?.setColorFilter(
            android.graphics.Color.WHITE,
            android.graphics.PorterDuff.Mode.SRC_ATOP
        )



        val imageView = findViewById<ImageView>(R.id.imageView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val textRating = findViewById<TextView>(R.id.textRating)
        val textUploadDate = findViewById<TextView>(R.id.textUploadDate)
        val textTrendingRank = findViewById<TextView>(R.id.textTrendingRank)

        val url = intent.getStringExtra("gif_url")
        val rating = intent.getStringExtra("gif_rating") ?: "N/A"
        val uploadDate = intent.getStringExtra("gif_upload_date") ?: "N/A"
        val trendingDate = intent.getStringExtra("gif_trending") ?: "N/A"

        textRating.text = "Rating: ${rating.uppercase()}"
        textUploadDate.text = "Uploaded: ${formatDate(uploadDate)}"

        if (trendingDate == "0000-00-00 00:00:00" || trendingDate == "N/A") {
            textTrendingRank.text = "Trending: Not trending"
        } else {
            textTrendingRank.text = "Trending since: ${formatDate(trendingDate)}"
        }

        // show loader
        progressBar.visibility = View.VISIBLE

        // load gif
        imageView.load(url) {
            listener(
                onSuccess = { _, _ ->
                    progressBar.visibility = View.GONE
                },
                onError = { _, _ ->
                    progressBar.visibility = View.GONE
                }
            )
        }
    }


    // date edit
    private fun formatDate(raw: String): String {
        return try {
            val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
            val outputFormat = java.text.SimpleDateFormat("MMM d, yyyy", java.util.Locale.getDefault())
            val date = inputFormat.parse(raw)
            if (date != null) outputFormat.format(date) else raw
        } catch (e: Exception) {
            raw
        }
    }

    //back button
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}