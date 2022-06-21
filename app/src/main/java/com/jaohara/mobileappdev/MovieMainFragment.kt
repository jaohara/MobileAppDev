package com.jaohara.mobileappdev

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

// fragment databinding class
import com.jaohara.mobileappdev.databinding.FragmentMovieMainBinding;
import com.jaohara.mobileappdev.movie.MovieListAdapter

/*
  The fragment class for the Main Movie screen, which is the main view for HW3.
 */
class MovieMainFragment : Fragment(){
  private var _binding: FragmentMovieMainBinding? = null;
  private val binding get() = _binding!!;


  // TODO: Figure out a way to make this reusable and put it in JohnTools
  private fun changeNavTitle () {
    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar;
    actionBar?.title = getString(R.string.hw3);
    actionBar?.setDisplayHomeAsUpEnabled(true);
    actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.hw3)));
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {

    }

    changeNavTitle();
  }

  override fun onResume() {
    super.onResume();
    changeNavTitle();
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentMovieMainBinding.inflate(inflater, container, false);
    return binding.root;
  }

  override fun onDestroyView() {
    super.onDestroyView();
    _binding = null;
  }

  fun getMovieNav(): NavController {
    return this.findNavController();
  }

  companion object {

    @JvmStatic
    fun newInstance(param1: String, param2: String) =
      MovieMainFragment().apply {
        arguments = Bundle().apply {

        }
      }
  }

  // Interface created for Navigation
  interface OnFragmentInteractionListener {
    fun onFragmentInteraction(uri: Uri)
  }
}