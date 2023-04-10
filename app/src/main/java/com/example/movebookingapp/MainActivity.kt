package com.example.movebookingapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException
import kotlin.random.Random

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    // Create an instance of the adapter to be used in the recycler view
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the hero image using Picasso and a random seed to get a different image each time the app is opened
        val heroImage = findViewById<ImageView>(R.id.hero_image)
        val randomSeed = Random.nextInt(1000)
        val url2 = "https://picsum.photos/seed/$randomSeed/2080/920?grayscale&blur=10"
        Picasso.get().load(url2).into(heroImage)

        // My URL to the JSON file with the movie data
        val url = "https://gist.githubusercontent.com/gdsduarte/780d6341ace6fb7ac8a5029213c42e0d/raw/515c4836b7a4771046dae4ade102e86074c99e0a/movie.json"

        // Create the adapter and pass in the click listener lambda function
        movieAdapter = MovieAdapter(emptyList()) { movie ->
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("movie", movie)
            startActivityForResult(intent, MOVIE_ACTIVITY_REQUEST_CODE)
        }

        // Create the recycler view and set the adapter and layout manager
        val recyclerView: RecyclerView = findViewById(R.id.movie_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter

        // Make the HTTP request to get the movie data
        url.makeHTTPRequest()
    }

    // This method makes an HTTP request to the given URL and updates the UI with the data
    private fun String.makeHTTPRequest() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(this)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "Failed to make HTTP request")
            }

            // This method is called when the HTTP request is successful
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val gson = Gson()
                    val movies: Array<Movie> = gson.fromJson(body, Array<Movie>::class.java)

                    // Update the seats remaining for each movie
                    movies.forEach { movie ->
                        movie.seatsRemaining = Random.nextInt(0, 16)
                    }

                    // Update the UI with the data
                    runOnUiThread {
                        movieAdapter.updateMovies(movies.toList())
                    }
                }
            }
        })
    }

    // This method is called when the MovieActivity finishes and returns the updated movie data
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if the request code and result code are correct and update the movie data
        if (requestCode == MOVIE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val updatedMovie = data?.getSerializableExtra("updatedMovie") as? Movie
            updatedMovie?.let {
                movieAdapter.updateMovie(it)
            }
        }
    }

    // Companion object to store the request code for the MovieActivity
    companion object {
        const val MOVIE_ACTIVITY_REQUEST_CODE = 1001
    }
}

