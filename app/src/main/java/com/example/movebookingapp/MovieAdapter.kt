package com.example.movebookingapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private var movies: List<Movie>, private val onItemClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {

    // Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    // Replace the contents of a view
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }

    // Return the size of the dataset
    override fun getItemCount(): Int = movies.size

    // Update the list of movies in the adapter with a new list of movies
    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    // Update a single movie in the list of movies in the adapter with a new movie
    @SuppressLint("NotifyDataSetChanged")
    fun updateMovie(updatedMovie: Movie) {
        val index = movies.indexOfFirst { it.id == updatedMovie.id }
        if (index != -1) {
            val updatedMovies = movies.toMutableList()
            updatedMovies[index] = updatedMovie
            movies = updatedMovies
            notifyDataSetChanged()
        }
    }
}



