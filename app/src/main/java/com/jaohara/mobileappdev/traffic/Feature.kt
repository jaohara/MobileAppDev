package com.jaohara.mobileappdev.traffic

/*
    Note to self - this is a result of that weird JSON having the response assigned as
    an array named Features.

    This is a data class to store that returned collection. It stores a List of the Camera
    data class, which is the nested, actual camera info.
 */
data class Feature(
    val Cameras: List<Camera>,
    val PointCoordinate: List<Double>
)