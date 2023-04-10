package com.example.movebookingapp

import java.io.Serializable

data class Movie(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val certification: String,
    val actors: String,
    val description: String,
    val runningTime: String,
    var seatsRemaining: Int,
    var seatsSelected: Int,
) : Serializable

