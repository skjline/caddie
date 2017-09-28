package com.skjline.caddie.common.utils

import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.GoogleMap
import com.skjline.caddie.common.model.Stroke

/**
 * Created on 9/28/17.
 */

fun Stroke.toLocation() : Location {
    val location = Location(LocationManager.GPS_PROVIDER)
    location.latitude = latitude
    location.longitude = longitude

    return location
}

fun GoogleMap.getCameraCenter() : Location {
    val location = Location(LocationManager.GPS_PROVIDER)
    location.latitude = cameraPosition.target.latitude
    location.longitude = cameraPosition.target.longitude

    return location
}