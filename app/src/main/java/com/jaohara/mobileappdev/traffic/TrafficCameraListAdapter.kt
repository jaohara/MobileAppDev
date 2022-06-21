package com.jaohara.mobileappdev.traffic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jaohara.mobileappdev.R
import com.squareup.picasso.Picasso

class TrafficCameraListAdapter(val context: Context, private val cameras: List<Camera>) :
  RecyclerView.Adapter<TrafficCameraListAdapter.TrafficCameraListViewHolder>() {

  class TrafficCameraListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    var cameraDescription: TextView? = v.findViewById<TextView>(R.id.cameraDescription);
    var cameraImage: ImageView? = v.findViewById<ImageView>(R.id.cameraImage);
    lateinit var cameraType: String;
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrafficCameraListViewHolder {
    val inflater = LayoutInflater.from(context);
    val view = inflater.inflate(R.layout.traffic_list_row, parent, false);
    return TrafficCameraListViewHolder(view);
  }

  override fun onBindViewHolder(holder: TrafficCameraListViewHolder, position: Int) {
    holder.cameraDescription?.text = cameras[position].Description;
    holder.cameraType = cameras[position].Type;

    val baseUrl = when (holder.cameraType) {
      "sdot" -> Camera.SDOT_URL
      else -> Camera.WSDOT_URL
    }

    val imageUrl = cameras[position].ImageUrl;

    // remember - baseUrl already has terminal slash appended
    Picasso.get().load("$baseUrl$imageUrl").into(holder.cameraImage);
  }

  override fun getItemCount(): Int {
    return cameras.count();
  }
}