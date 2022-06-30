package com.jaohara.mobileappdev

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

class FirebaseFragment : Fragment() {

  private fun changeNavTitle () {
    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar;
    actionBar?.title = getString(R.string.hw6);
    actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.hw6)));
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState);
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    changeNavTitle();
    return inflater.inflate(R.layout.fragment_firebase, container, false)
  }

  companion object {
    @JvmStatic
    fun newInstance() =
      FirebaseFragment().apply {
        arguments = Bundle().apply {
        }
      }
  }
}