package com.jaohara.mobileappdev.traffic

data class Camera(
    val Description: String,
    val Id: String,
    val ImageUrl: String,
    val Type: String
) {
    companion object {
        val SDOT_URL    = "https://www.seattle.gov/trafficcams/images/";
        val WSDOT_URL   = "https://images.wsdot.wa.gov/nw/";
    }
}