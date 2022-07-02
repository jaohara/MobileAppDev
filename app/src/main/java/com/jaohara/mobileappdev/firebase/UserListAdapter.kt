package com.jaohara.mobileappdev.firebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jaohara.mobileappdev.FirebaseFragment
import com.jaohara.mobileappdev.firebase.User;
import com.jaohara.mobileappdev.R

class UserListAdapter(val context: Context, private val userList: List<User>):
  RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

  class UserListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    var title: TextView? = v.findViewById<TextView>(R.id.item_title);
    var subTitle: TextView? = v.findViewById<TextView>(R.id.item_subtitle);
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
    val inflater = LayoutInflater.from(context);
    val view = inflater.inflate(R.layout.firebase_list_row, parent, false);
    return UserListViewHolder(view);
  }

  override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
    val updated = userList.get(position).updated;
    holder.title?.text = userList.get(position).username;
    holder.subTitle?.text  = "Updated: $updated";
  }

  override fun getItemCount(): Int {
    return userList.count();
  }
}