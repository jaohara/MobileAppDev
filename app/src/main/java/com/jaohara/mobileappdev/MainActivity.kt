@file:Suppress("RedundantSemicolon")

package com.jaohara.mobileappdev

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation;
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController

class MainActivity : AppCompatActivity(), MovieMainFragment.OnFragmentInteractionListener {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState);
//    setupNavTitles();
    setContentView(R.layout.activity_main);
  }

  // TODO: Is this the best place to set navbar titles?
  private fun setupNavTitles() {
    val navController = Navigation.findNavController(this, R.id.main_nav_host_fragment)
  }

  // created to implement communication between fragments and activities
  override fun onFragmentInteraction(uri: Uri) {

  }
}