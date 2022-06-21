package com.jaohara.mobileappdev.movie

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jaohara.mobileappdev.JohnTools
import com.jaohara.mobileappdev.R
import com.jaohara.mobileappdev.databinding.FragmentMovieDetailBinding
import com.squareup.picasso.Picasso


class MovieDetail() : Fragment() {
  private var _binding: FragmentMovieDetailBinding? = null;
  private val binding get() = _binding!!;

  private fun changeNavTitle () {
    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar;
    actionBar?.title = getString(R.string.hw3);
    actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.hw3)));
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentMovieDetailBinding.inflate(inflater, container, false);

    Log.i("onCreateView", "checking bundle...");
    val bundle = this.arguments;

    bundle?.let {
      val position = it.get("position") as Int;

      // Going forward under the assumption that every movie has these
      binding.movieDetailTitle.text = Movie.movies[position][0];
      binding.movieDetailYear.text = Movie.movies[position][1];
      binding.movieDetailDescription.text = Movie.movies[position][4];

      // I don't like how this has to make a request every time - how can I save this image?
      context?.let {
        when (JohnTools.isNetworkConnected(it)) {
          true -> Picasso.get().load(Movie.movies[position][3]).into(binding.movieImage);
          false -> {
            Toast.makeText(it,
              "Network is disconnected - movie images will not be loaded.",
              Toast.LENGTH_LONG).show();

            // TODO: I think INVISIBLE keeps my constraints? Look into that.
            binding.movieImage.visibility = View.INVISIBLE;
          }
        }
      }

      // this seems to be inconsistent, so account for that
      when (Movie.movies[position][2].length) {
        0     -> {
          binding.movieDetailDirector.visibility = View.INVISIBLE;
          binding.movieDetailDirectorLabel.visibility = View.INVISIBLE;
        }
        else  -> { binding.movieDetailDirector.text = Movie.movies[position][2] }
      }
    }

    return binding.root;
  }

  companion object {
    @JvmStatic
    fun newInstance(movieDetailArray: Array<String>?) =
      MovieDetail().apply {
        arguments = Bundle().apply {
          putStringArray("movieDetailArray", movieDetailArray);
        }
      }
  }
}