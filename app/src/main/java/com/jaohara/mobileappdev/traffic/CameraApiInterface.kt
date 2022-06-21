package com.jaohara.mobileappdev.traffic

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CameraApiInterface {
  companion object {
    const val BASE_URL = "https://web6.seattle.gov/Travelers/";
  }

  // expected params:
  //  zoomId=13  -> "zoomId" as Int
  //  type=2     -> "type" as Int
  @GET("api/Map/Data")
  fun getData(
    @Query("zoomId") zoomId: Int = 13,
    @Query("type") type: Int = 2
//  ): Call<List<Feature>>;
  ): Call<CameraData>;
}