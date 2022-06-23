package com.jaohara.mobileappdev

import android.graphics.drawable.ColorDrawable
import androidx.navigation.Navigation;
import android.os.Bundle
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

    binding.hw3Button.setOnClickListener{
      Navigation.findNavController(it).navigate(R.id.mainToMovieMain);
    }

    binding.hw4Button.setOnClickListener{
      Navigation.findNavController(it).navigate(R.id.mainToTrafficMain);
    }

    binding.hw5Button.setOnClickListener{
      Navigation.findNavController(it).navigate(R.id.mainToMaps);
    }
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