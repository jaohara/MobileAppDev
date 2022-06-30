package com.jaohara.mobileappdev

import android.graphics.drawable.ColorDrawable
import androidx.navigation.Navigation;
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

// fragment databinding class
import com.jaohara.mobileappdev.databinding.FragmentMainBinding;

/*
  The fragment class for the Main app screen, which is the default landing for the Main Activity's
  Navigation Graph.
 */
class MainFragment : Fragment() {
  private var _binding: FragmentMainBinding? = null;
  private val binding get() = _binding!!

  private fun changeNavTitle () {
    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar;
    actionBar?.title = getString(R.string.app_name);
    actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.gray_light)));
  }

  private fun signIn() {
    Log.d("FIREBASE", "signIn");

    /*
      From signin.java:

        1. Validate display name, email, and password entries
        2. Save valid entries to shared preferences
        3. sign into firebase
    */


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
    // NOTE: Modified to use view binding rather than inflating the layout manually
    _binding = FragmentMainBinding.inflate(inflater, container, false);
    return binding.root;
  }

  override fun onDestroyView() {
    super.onDestroyView();
    _binding = null;
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    /*
      MAIN NAVIGATION LISTENER SETUP
     */

    val navButtons = arrayOf(
      binding.hw3Button, // 0
      binding.hw4Button, // 1
      binding.hw5Button, // 2
      binding.hw6Button  // 3
    );

    for (i in 0..(navButtons.size - 1)) {
      val navActionId = when (i) {
        0 -> R.id.mainToMovieMain;
        1 -> R.id.mainToTrafficMain;
        2 -> R.id.mainToMaps;
        3 -> R.id.mainToFirebase;
        else -> null;
      }

      navActionId?.let { id ->
        navButtons[i].setOnClickListener {
          Navigation.findNavController(it).navigate(id);
        }
      }
    }

    // old way - remove when you're totally certain about above
//    binding.hw3Button.setOnClickListener{
//      Navigation.findNavController(it).navigate(R.id.mainToMovieMain);
//    }
//
//    binding.hw4Button.setOnClickListener{
//      Navigation.findNavController(it).navigate(R.id.mainToTrafficMain);
//    }
//
//    binding.hw5Button.setOnClickListener{
//      Navigation.findNavController(it).navigate(R.id.mainToMaps);
//    }
//
//    binding.hw6Button.setOnClickListener{
//      Navigation.findNavController(it).navigate(R.id.mainToFirebase);
//    }
  }

  companion object {

    @JvmStatic
    fun newInstance() =
      MainFragment().apply {
        arguments = Bundle().apply {

        }
      }
  }
}