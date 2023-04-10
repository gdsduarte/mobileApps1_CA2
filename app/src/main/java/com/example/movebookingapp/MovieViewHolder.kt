package com.example.movebookingapp

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Get references to the views in the layout
    private val movieImage: ImageView = itemView.findViewById(R.id.movie_image)
    private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
    private val movieClassification: TextView = itemView.findViewById(R.id.movie_certification)
    private val movieActors: TextView = itemView.findViewById(R.id.movie_actors)
    private val movieRunningTime: TextView = itemView.findViewById(R.id.movie_running_time)
    private val movieSeatsRemaining: TextView = itemView.findViewById(R.id.movie_seats_remaining)
    private val movieSeatIcon: ImageView = itemView.findViewById(R.id.movie_Seat_Icon)

    // Bind the data to the views in the layout
    fun bind(movie: Movie) {

        // Load the image using Picasso library
        Picasso.get()
            .load(movie.imageUrl)
            .into(movieImage)

        // Set the text of the views to the data in the movie object
        movieTitle.text = movie.title
        movieClassification.text = movie.certification
        movieActors.text = movie.actors
        movieRunningTime.text = movie.runningTime

        // Set the text color of the seats remaining text view
        when {
            movie.seatsSelected > 0 -> {
                movieSeatsRemaining.text = itemView.context.getString(R.string.seats_selected, movie.seatsSelected)
                movieSeatsRemaining.setTextColor(Color.GREEN)
                movieSeatIcon.setColorFilter(Color.GREEN)
            }
            movie.seatsRemaining == 0 -> {
                movieSeatsRemaining.text = itemView.context.getString(R.string.sold_out)
                movieSeatsRemaining.setTextColor(Color.parseColor("#CAC4D0"))
                movieSeatIcon.clearColorFilter()
            }
            else -> {
                movieSeatsRemaining.text = itemView.context.getString(R.string.seats_remaining, movie.seatsRemaining)
                movieSeatsRemaining.setTextColor(Color.parseColor("#CAC4D0"))
                movieSeatIcon.clearColorFilter()
            }
        }

        // Show or hide the filling fast badge depending on the number of seats remaining
        val fillingFastBadge: TextView = itemView.findViewById(R.id.movie_badge)
        if (movie.seatsRemaining in 1..2) {
            fillingFastBadge.visibility = View.VISIBLE
        } else {
            fillingFastBadge.visibility = View.GONE
        }
    }
}
