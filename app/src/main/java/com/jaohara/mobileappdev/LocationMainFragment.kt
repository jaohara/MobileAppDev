package com.jaohara.mobileappdev

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.location.LocationListenerCompat
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jaohara.mobileappdev.databinding.FragmentLocationMainBinding
import com.jaohara.mobileappdev.traffic.Camera
import com.jaohara.mobileappdev.traffic.CameraData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationMainFragment : Fragment(),
  GoogleMap.OnMarkerClickListener, OnMapReadyCallback, LocationListenerCompat {
  private var _binding: FragmentLocationMainBinding? = null;
  private val binding get() = _binding!!;

  private lateinit var locationManager: LocationManager;
  private lateinit var map: GoogleMap;

  // kinda gross, I don't like this
  private var initialLocation: LatLng? = null;

  private fun changeNavTitle () {
    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar;
    actionBar?.title = getString(R.string.hw5);
    actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.hw5)));
  }

  private fun getLocation() {
    Log.i("LocationMainFragment::getLocation", "Calling getLocation");
    // TODO: Do I need to have better feedback in the event that context doesn't exist? Will it ever?
    context?.let {
      locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager;

      // check for location permissions
      val currentLocationPermissions =
        ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION);

      if (currentLocationPermissions != PackageManager.PERMISSION_GRANTED) {
        // what's up with that permission code passed in?
        val permissionCode = 2;
        // TODO: this is deprecated, is there a better way? registerForActivityResult?
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode);
      }

      // I think
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 5f, this);
    }
  }

  private fun getCamerasAndAddToMap(map: GoogleMap) {
    Log.i("LocationMainFragment::getCamerasAndAddToMap", "Calling getCamerasAndAddToMap");
    JohnTools.getCameraDataCall().enqueue(object : Callback<CameraData?> {
      override fun onResponse(call: Call<CameraData?>, response: Response<CameraData?>) {
        val responseBody = response.body()!!;
        val cameraList: MutableList<Camera> = mutableListOf();

        /*

          TODO: RESUME HERE AFTER YOU GET BACK FROM WORK!

            - You were trying to add all of the coords from the features to the map
            - The coords need to be passed as a LatLng() object to position
            - Title should be... well, I haven't decided yet. The address, right?


         */

        for (feature in responseBody.Features) {
          val markerCoords = LatLng(feature.PointCoordinate[0], feature.PointCoordinate[1]);
          // defaulting to first intersection for description
          val markerDesc = feature.Cameras[0].Description;

          map.addMarker(
            MarkerOptions()
              .position(markerCoords)
              .title(markerDesc)
          );
        }

//        map.moveCamera(CameraUpdateFactory.newLatLng(location.))
      }

      override fun onFailure(call: Call<CameraData?>, t: Throwable) {
        val message = t.message;
        Log.d("LocationMainFragment", "Failure: $message");
      }
    })
  }

  /*
      TODO: Add this code to your fragment, replacing the id with whatever you want:

      <fragment
            android:id="@+id/map"
            android:name="com.google.android.gms.maps.SupportMapFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

   */

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState);

    // TODO: This needs a fallback if the network isn't connected. How was I doing that again?
    getLocation();

    changeNavTitle();
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    Log.i("LocationMainFragment::onMarkerClick", "Calling onMarkerClick");
    TODO("Not yet implemented")
  }

  override fun onMapReady(map: GoogleMap) {
    Log.i("LocationMainFragment", "onMapReady has fired");

    context?.let {
      when (JohnTools.isNetworkConnected(it)) {
        true  -> {
          this.map = map;
          getCamerasAndAddToMap(map);
        };
        false -> JohnTools.displayNetworkDisconnectedDialog(it);
      }
    }
  }


  override fun onLocationChanged(location: Location) {
    if (this.initialLocation == null && map != null) {
      Log.i("LocationMainFragment::onLocationChanged", "setting initialLocation:")
      val initialLocation = LatLng(location.latitude, location.longitude);
      this.initialLocation = initialLocation;
      Log.i("LocationMainFragment::onLocationChanged", "initialLocation is $initialLocation")
      map.moveCamera(CameraUpdateFactory.newLatLng(initialLocation));
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentLocationMainBinding.inflate(inflater, container, false);
    return binding.root;
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null;
  }

  override fun onResume() {
    super.onResume();
    changeNavTitle();
  }

  // TODO: Look into what this is used for
  companion object {
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
      LocationMainFragment().apply {
        arguments = Bundle().apply {

        }
      }
  }
}