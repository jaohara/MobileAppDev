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
    setupNavTitles();
    setupNavBarColor();
    setContentView(R.layout.activity_main);

  }

  private fun setupNavBarColor() {
    window.navigationBarColor = getColor(R.color.gray_light);
  }

  // TODO: Is this the best place to set navbar titles?
  private fun setupNavTitles() {
    supportActionBar?.setDisplayHomeAsUpEnabled(true);
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed();
    return true;
  }

  // created to implement communication between fragments and activities
  override fun onFragmentInteraction(uri: Uri) {

  }
}