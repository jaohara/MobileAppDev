package com.jaohara.mobileappdev

// fragment databinding class

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.jaohara.mobileappdev.databinding.FragmentMainBinding


/*
  The fragment class for the Main app screen, which is the default landing for the Main Activity's
  Navigation Graph.
 */
class MainFragment : Fragment() {
  private var _binding: FragmentMainBinding? = null;
  private val binding get() = _binding!!

  private lateinit var sharedPrefs: SharedPreferences;

  private fun changeNavTitle () {
    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar;
    actionBar?.title = getString(R.string.app_name);
    actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.gray_light)));
  }

  // TODO: What am I validating here? That the email is an email?
  private fun validateInput(): Boolean {
    return true;
  }

  private fun signIn() {
    Log.d("FIREBASE", "signIn");

    // disable login button
    binding.Login.isEnabled = false;

    // 1. Validate display name, email, and password entries
    if (!validateInput()) {
      Log.d("FireBase::signIn", "validateInput failed.");
      binding.Login.isEnabled = true;
      return;
    }

    val displayName = binding.loginUsername.text.toString();
    val email = binding.loginEmail.text.toString();
    val password = binding.loginPassword.text.toString();

    // 2. Save valid entries to shared preferences
    val editor = sharedPrefs.edit();
    editor.putString("username", displayName);
    editor.putString("email", email);
    editor.putString("password", password);
    editor.commit();

    // 3. sign in to firebase
    activity?.let {
      FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(it,
          OnCompleteListener<AuthResult?> { task ->
            Log.d("FIREBASE", "signIn:onComplete:" + task.isSuccessful);

            if (task.isSuccessful) {
              // update profile. displayName is the value entered in UI
              val user = FirebaseAuth.getInstance().currentUser
              val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build();
              user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                  if (task.isSuccessful) {
                    Log.d("FIREBASE", "User profile updated.")
                    // Go to FirebaseActivity
//                    startActivity(Intent(this@MainActivity, FirebaseActivity::class.java))
                    findNavController().navigate(R.id.mainToFirebase);
                  }
                }
            }
            else {
              Log.d("FIREBASE", "sign-in failed");
              JohnTools.displayFirebaseFailureToast(it);
              binding.Login.isEnabled = true;
            }
          });
    }

  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
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
    binding.Login.isEnabled = true;

    // set textedit fields to previous sharedPrefs
    context?.let {
      sharedPrefs = it.getSharedPreferences("preferences", Context.MODE_PRIVATE);
      binding.loginUsername.setText(sharedPrefs.getString("username", ""));
      binding.loginEmail.setText(sharedPrefs.getString("email", ""));
      binding.loginPassword.setText(sharedPrefs.getString("password", ""));
    }

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

    // login button setup
    binding.Login.setOnClickListener { signIn() }

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