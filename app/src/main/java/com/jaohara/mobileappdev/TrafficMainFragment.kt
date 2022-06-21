package com.jaohara.mobileappdev

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaohara.mobileappdev.databinding.FragmentTrafficMainBinding
import com.jaohara.mobileappdev.traffic.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TrafficMainFragment : Fragment() {
  private var _binding: FragmentTrafficMainBinding? = null;
  private val binding get() = _binding!!;

  private fun changeNavTitle() {
    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar;
    actionBar?.title = getString(R.string.hw4);
    actionBar?.setDisplayHomeAsUpEnabled(true);
    actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.hw4)));
  }

  // TODO: Could I cut out more reused code?
  private fun getCameras() {
    JohnTools.getCameraDataCall().enqueue(object : Callback<CameraData?> {
      override fun onResponse(call: Call<CameraData?>, response: Response<CameraData?>) {
        val responseBody = response.body()!!;
        val cameraList: MutableList<Camera> = mutableListOf();

        // Remember the strange structure here - the json response is an object, not an array.
        for (feature in responseBody.Features) {
          for (camera in feature.Cameras) {
            cameraList.add(camera);
          }
        }

        binding.trafficRecyclerView.adapter = context?.let {
          TrafficCameraListAdapter(it, cameraList.toList());
        }

        binding.trafficRecyclerView.layoutManager = context?.let{ LinearLayoutManager(it) }
      }

      override fun onFailure(call: Call<CameraData?>, t: Throwable) {
        val message = t.message;
        Log.d("TrafficMainFragment", "Failure: $message");
      }
    });
  }


  private fun showNetworkErrorToast() {
    Toast.makeText(context,
      "Sorry, network connectivity must be activated to use these features.",
      Toast.LENGTH_LONG
    ).show();
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    context?.let {
      when (JohnTools.isNetworkConnected(it)) {
        true  -> getCameras();
        false -> JohnTools.displayNetworkDisconnectedDialog(it);
      }
    }

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
    _binding = FragmentTrafficMainBinding.inflate(inflater, container, false);
    return binding.root;
  }

  override fun onDestroyView() {
    super.onDestroyView();
    _binding = null;
  }

  companion object {
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
      TrafficMainFragment().apply {
        arguments = Bundle().apply {

        }
      }
  }
}