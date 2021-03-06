package com.oliverperez.blockbusterchallenge.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.oliverperez.blockbusterchallenge.model.Movie
import com.oliverperez.blockbusterchallenge.R
import com.oliverperez.blockbusterchallenge.ui.dashboard.MovieDashboardActivity
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    lateinit var movie: Movie
    lateinit var poster: ImageView
    lateinit var releaseDate: TextView
    lateinit var titleLabel: TextView
    lateinit var summaryLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        movie = intent.getParcelableExtra(MovieDashboardActivity.INTENT_MOVIE_KEY) as Movie
        title = movie.title
        poster = poster_detail
        releaseDate = release_date_text
        titleLabel = movie_detail_title
        summaryLabel = summary_text_view

        poster.setImageResource(movie.poster)
        titleLabel.text = movie.title
        releaseDate.text = movie.releaseDate
        summaryLabel.text = movie.summary
    }
}