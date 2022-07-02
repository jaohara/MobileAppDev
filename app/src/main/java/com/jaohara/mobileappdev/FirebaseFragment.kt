package com.jaohara.mobileappdev

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.jaohara.mobileappdev.databinding.FragmentFirebaseBinding
import com.jaohara.mobileappdev.firebase.User
import com.jaohara.mobileappdev.firebase.UserListAdapter
import java.util.Date

class FirebaseFragment : Fragment() {
  private var _binding: FragmentFirebaseBinding? = null;
  private val binding get() = _binding!!;

  private val TAG = FirebaseFragment.javaClass.simpleName;

  private lateinit var myRef: DatabaseReference;

  val userData: ArrayList<User> = ArrayList<User>();

  private fun changeNavTitle () {
    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar;
    actionBar?.title = getString(R.string.hw6);
    actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.hw6)));
  }

  private fun writeNewUser(userId: String, name: String?, email: String?) {
    myRef.child(userId).setValue(User(name, email, Date().toString()));
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState);

    val mAuth = FirebaseAuth.getInstance();
    val mDatabase = FirebaseDatabase.getInstance();
    val currentUser = mAuth.currentUser;

    myRef = mDatabase.getReference("users");
    val members = myRef.orderByChild("updated");

    currentUser?.let{
      writeNewUser(it.uid, it.displayName, it.email);

      // get list of users
      members.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
          Log.d("FIREBASE", "onDataChange");

          userData.clear();

          for (userSnapshot in dataSnapshot.children) {
            val username = userSnapshot.child("username").value
            username?.let {
              Log.i("FireBase::onDataChange", "Adding $username to list");
              val user = User(
                username.toString(),
                userSnapshot.child("email").value.toString(),
                userSnapshot.child("updated").value.toString()
              )
              userData.add(user)
            }
          }

          // TODO: Data is in a strange order here? what am I doing wrong?

          userData.reverse()

          // I think I can reference the binding here, as this was asynchronous?
          binding?.let {
            binding.firebaseRecyclerView.adapter = context?.let { UserListAdapter(it, userData) }
            binding.firebaseRecyclerView.layoutManager = context?.let{ LinearLayoutManager(it) }
          }
        }

        override fun onCancelled(databaseError: DatabaseError) {
          Log.w(TAG, "loadUsers:onCancelled", databaseError.toException())
        }
      })
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    changeNavTitle();
    _binding = FragmentFirebaseBinding.inflate(inflater, container, false);
    return binding.root;
  }

  override fun onDestroyView() {
    super.onDestroyView()
    userData.clear();
//    _binding = null;
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