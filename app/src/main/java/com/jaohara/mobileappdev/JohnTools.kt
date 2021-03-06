package com.jaohara.mobileappdev

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jaohara.mobileappdev.traffic.CameraApiInterface
import com.jaohara.mobileappdev.traffic.CameraData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JohnTools {
  companion object {
    fun isNetworkConnected(context: Context): Boolean {
      val conMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;

      if (conMgr.activeNetworkInfo == null) return false;

      return conMgr.activeNetworkInfo!!.type == ConnectivityManager.TYPE_WIFI ||
              conMgr.activeNetworkInfo!!.type == ConnectivityManager.TYPE_MOBILE;
    }

    fun displayNetworkDisconnectedDialog(context: Context): Unit {
      AlertDialog.Builder(context)
        .setTitle("No Network Access")
        .setMessage(R.string.network_disconnected_message)
        .show();
    }

    fun displayLocationDialog(context: Context): Unit {
      AlertDialog.Builder(context)
        .setTitle("No Location Access")
        .setMessage(R.string.location_disconnected_message)
        .show();
    }

    fun displayFirebaseFailureToast(activity: Activity) {
      Toast.makeText(activity, "Sign In Failed", Toast.LENGTH_SHORT).show();
    }

    // Queries cameras
    fun getCameraDataCall(): Call<CameraData> {
      val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(CameraApiInterface.BASE_URL)
        .build()
        .create(CameraApiInterface::class.java);

      return retrofitBuilder.getData();
    }
  }
}