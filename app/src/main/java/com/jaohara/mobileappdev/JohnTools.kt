package com.jaohara.mobileappdev

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

    // Queries cameras
    fun getCameraDataCall(): Call<CameraData> {
      val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(CameraApiInterface.BASE_URL)
        .build()
        .create(CameraApiInterface::class.java);

      return retrofitBuilder.getData();
    }

    // The idea here would be to have this accept a lambda so you can have this
    //  handle executing the callback rather than using an if statement. Maybe not
    //  as useful as I imagine.
//    fun callIfNetworkIsConnected()
  }
}