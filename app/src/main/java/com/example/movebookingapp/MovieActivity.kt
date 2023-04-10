package com.example.movebookingapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        // Get the movie object from the intent extras (if it exists) and cast it to a Movie object
        val movie: Movie? = intent.getSerializableExtra("movie") as? Movie

        // Check if the movie object is not null
        if (movie != null) {
            // Call the setupMovieData method to set the UI elements with the movie data from the object we just created above (movie)
            setupMovieData(movie)
        } else {
            // If the movie object is null, log an error message
            Log.e("MovieActivity", "Movie object is null")
        }
    }

    @SuppressLint("SetTextI18n", "WrongViewCast")
    private fun setupMovieData(movie: Movie) {

        // Find UI elements and assign them to variables for later use in the method
        val backButton: MaterialButton = findViewById(R.id.imgBtnBack)
        val movieImage: ImageView = findViewById(R.id.movie_image)
        val movieTitle: TextView = findViewById(R.id.movie_title)
        val movieCertification: TextView = findViewById(R.id.movie_certification)
        val movieActors: TextView = findViewById(R.id.movie_actors)
        val movieDescription: TextView = findViewById(R.id.movie_description)
        val movieRunningTime: TextView = findViewById(R.id.movie_running_time)
        val seatsSelected: TextView = findViewById(R.id.selected_seats)
        val plusButton: MaterialButton = findViewById(R.id.plus_button)
        val minusButton: MaterialButton = findViewById(R.id.minus_button)
        val movieSeatsRemaining: TextView = findViewById(R.id.movie_seats_remaining)

        // Load the image into the image view using Picasso
        Picasso.get()
            .load(movie.imageUrl)
            .into(movieImage)

        // Set text values for the text views
        movieTitle.text = movie.title
        movieCertification.text = movie.certification
        movieActors.text = movie.actors
        movieDescription.text = movie.description
        movieRunningTime.text = movie.runningTime
        seatsSelected.text = movie.seatsSelected.toString()

        // Check if the movie is sold out and set the text view accordingly
        if (movie.seatsRemaining == 0) {
            movieSeatsRemaining.text = getString(R.string.sold_out)
        } else {
            movieSeatsRemaining.text = getString(R.string.seats_remaining, movie.seatsRemaining)
        }

        // Set back button click listener to pass the updated movie object back to the main activity
        backButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("updatedMovie", movie)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // Update button states based on the number of seats selected and remaining
        fun updateButtonStates() {
            plusButton.isEnabled = movie.seatsRemaining > 0
            minusButton.isEnabled = movie.seatsSelected > 0
        }

        // Call the updateButtonStates method to set the initial button states
        updateButtonStates()

        // Set button click listeners to update the number of seats selected and remaining
        plusButton.setOnClickListener {
            if (movie.seatsRemaining > 0) {
                movie.seatsSelected++
                movie.seatsRemaining--
                seatsSelected.text = movie.seatsSelected.toString()
                movieSeatsRemaining.text = getString(R.string.seats_remaining, movie.seatsRemaining)
                updateButtonStates()
            }
        }

        minusButton.setOnClickListener {
            if (movie.seatsSelected > 0) {
                movie.seatsSelected--
                movie.seatsRemaining++
                seatsSelected.text = movie.seatsSelected.toString()
                movieSeatsRemaining.text = getString(R.string.seats_remaining, movie.seatsRemaining)
                updateButtonStates()
            }
        }
    }

    // Override the back button to pass the updated movie object back to the main activity
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val movie: Movie? = intent.getSerializableExtra("movie") as? Movie
        if (movie != null) {
            val resultIntent = Intent()
            resultIntent.putExtra("updatedMovie", movie)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        super.onBackPressed()
    }
}

