package com.jaohara.mobileappdev

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationListenerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jaohara.mobileappdev.traffic.CameraData
import com.jaohara.mobileappdev.traffic.Feature
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener, LocationListenerCompat {
  private val LOCATION_REQ_CODE = 1234321;

  private val featureList: MutableList<Feature> = mutableListOf();
  private var currentLocation: Location? = null;
  private lateinit var fusedLocationClient: FusedLocationProviderClient;
  private var mapFragment: SupportMapFragment? = null;

  private fun changeNavTitle () {
    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar;
    actionBar?.title = getString(R.string.hw5);
    actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.hw5)));
  }

  private fun hasLocationPermissions(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
  }

  private fun getLocation() {
    Log.i("MapsFragment::getLocation", "Calling getLocation");

    context?.let { context ->
      when (hasLocationPermissions(context)) {
        true -> getLastLocation();
        false -> askForLocationPermission();
      }
    }
  }

  private fun addCameraMarkers() {
    mapFragment?.getMapAsync {
      Log.i("MapsFragment::addCameraMarkers", "Calling addCameraMarkers");
      for (feature in featureList) {
        val coords = LatLng(feature.PointCoordinate[0], feature.PointCoordinate[1])
        Log.i("MapsFragment::addCameraMarkers", "Adding $coords");
        it.addMarker(
          MarkerOptions()
            .position(coords)
            .title(feature.Cameras[0].Description) // making an assumption here
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        );
      }
    };
  }

  private fun updateMapMarker(googleMap: GoogleMap, location: Location) {
    Log.i("MapsFragment::updateMapMarker", "Calling updateMapMarker");
    val coords = LatLng(location.latitude, location.longitude);
    googleMap.addMarker(
      MarkerOptions()
        .position(coords)
        .title("Current Location")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
    );
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(coords));
  }

  private fun getLastLocation() {
    fusedLocationClient.lastLocation
      .addOnSuccessListener { location: Location? ->
        currentLocation = location;
        Log.i("MapsFragment::getLastLocation", "Set currentLocation to $currentLocation");

        if (location != null) {
          mapFragment?.let { it.getMapAsync { updateMapMarker(it, location) } }
        }
      }
  }

  // TODO: clean up commented code
  private fun askForLocationPermission() {
    Log.i("MapsFragment::askForLocationPermission", "In askForLocationPermission");
    context?.let { context ->
      activity?.let {
        JohnTools.displayLocationDialog(context);
        ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQ_CODE);
      }
    }
  }

  private fun getCameras() {
    JohnTools.getCameraDataCall().enqueue(object : Callback<CameraData?> {
      override fun onResponse(call: Call<CameraData?>, response: Response<CameraData?>) {
        val responseBody = response.body()!!;

        for (feature in responseBody.Features) {
          featureList.add(feature)
        }

        addCameraMarkers();
      }

      override fun onFailure(call: Call<CameraData?>, t: Throwable) {
        val message = t.message;
        Log.d("MapsFragment::getCameras", "Failure: $message");
      }
    });
  }

  // TODO: Clean up
  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    if (requestCode == LOCATION_REQ_CODE) {
      when (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        true -> getLastLocation();
        false -> askForLocationPermission();
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  private val callback = OnMapReadyCallback { googleMap ->
    googleMap.moveCamera(CameraUpdateFactory.zoomTo(12.5F));
    googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL;
    googleMap.setMaxZoomPreference(18F);

    currentLocation?.let { currentLocation ->
      updateMapMarker(googleMap, currentLocation);
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    getCameras();
    getLocation();
    changeNavTitle();
    return inflater.inflate(R.layout.fragment_maps, container, false)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState);
    activity?.let {
      fusedLocationClient = LocationServices.getFusedLocationProviderClient(it);
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
    mapFragment?.getMapAsync(callback);
  }

  override fun onMarkerClick(p0: Marker): Boolean {
    TODO("Not yet implemented")
  }

  override fun onLocationChanged(location: Location) {
    // TODO: uncomment
    getLastLocation();
  }
}