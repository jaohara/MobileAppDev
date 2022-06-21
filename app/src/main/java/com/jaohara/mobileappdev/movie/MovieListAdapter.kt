package com.jaohara.mobileappdev.movie

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.jaohara.mobileappdev.R

class MovieListAdapter(val context: Context, private val onItemClicked: (position: Int) -> Unit) :
  RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {
  private val movies: Array<Array<String>> = Movie.movies;

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
    val inflater = LayoutInflater.from(context);
    val view = inflater.inflate(R.layout.movie_list_row, parent, false);
    return MovieListViewHolder(view, onItemClicked);
  }

  override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
    // attach values to the movie_list_row file based on the position of the recycler view
    holder.movieName?.text = movies[position][0];
    holder.movieYear?.text = movies[position][1];
  }

  override fun getItemCount(): Int {
    return movies.count();
  }

  fun getMovie(position: Int): Array<String> {
    return movies[position];
  }

  class MovieListViewHolder(
    v: View,
    private val onItemClicked: (position: Int) -> Unit
  ) : RecyclerView.ViewHolder(v), View.OnClickListener {
    // add a movie image?
    var movieName: TextView? = v.findViewById<TextView>(R.id.movieTitle);
    var movieYear: TextView? = v.findViewById<TextView>(R.id.movieYear);

    // Hahahaha, I love Android. There goes the last of my sanity.
    init {
      itemView.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
      val position = adapterPosition;
      Log.i("MovieListViewHolder.onClick", "Click registered for {position}");
      onItemClicked(position);
    }
  }
}

