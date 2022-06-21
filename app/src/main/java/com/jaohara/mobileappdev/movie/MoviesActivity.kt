package com.jaohara.mobileappdev.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.jaohara.mobileappdev.R

class MoviesActivity : AppCompatActivity() {
  var fragMovieList: Fragment? = null;
  var fragMovieDetailView: Fragment? = null;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies);

    fragMovieList =
      supportFragmentManager.findFragmentById(R.id.movie_list_view);
    fragMovieDetailView =
      supportFragmentManager.findFragmentById(R.id.movie_detail_view);


    if (savedInstanceState == null) {
      supportFragmentManager.commit {
        fragMovieDetailView?.let { hide(it) };
        setReorderingAllowed(true);
        add<MovieList>(
          R.id.movie_list_view, "movieList",
          bundleOf("movies" to Movie.movies));
      }
    }
  }
}