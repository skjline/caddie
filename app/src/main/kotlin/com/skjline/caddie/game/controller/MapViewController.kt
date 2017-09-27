package com.skjline.caddie.game.controller

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.skjline.caddie.R
import com.skjline.caddie.common.Const
import com.skjline.caddie.common.ViewController
import com.skjline.caddie.game.presenter.LocationHandler
import com.skjline.caddie.game.presenter.LocationPresenter
import io.reactivex.Observable

/**
 * Created on 9/15/17.
 */
class MapViewController(private val map: GoogleMap) : ViewController {

    lateinit var bitmap: Bitmap
    lateinit var location: LocationPresenter

    var anchor: Location? = null
    var listener: OnPositionUpdated? = null

    fun setPostionUpdateListener(l : OnPositionUpdated) {
        listener?.let {
            // already set
            Log.d(MapViewController::class.java.simpleName,
                    "Overwriting an existing listener")
        } ?: run { listener = l }
    }

    fun initializeMap(context: Context, lm: LocationManager) {
        map.setMinZoomPreference(10f)
        map.mapType = GoogleMap.MAP_TYPE_SATELLITE
        map.isMyLocationEnabled = true

        bitmap = bitmapDescriptorFromVector(context, R.drawable.ic_album_black_24dp)

        location = LocationHandler(lm)
        location.onPositionChanged().subscribe { loc ->
            anchor = loc
            map.animateCamera(CameraUpdateFactory.newCameraPosition(
                    CameraPosition.Builder()
                            .target(LatLng(loc.latitude, loc.longitude))
                            .zoom(17f)
                            .build()))

            listener?.onPositionUpdated(Const.Companion.POSITION_REF, loc)
        }

        map.setOnCameraMoveListener {
            val geo = Location(LocationManager.GPS_PROVIDER)
            geo.latitude = map.cameraPosition.target.latitude
            geo.longitude = map.cameraPosition.target.longitude

            listener?.onPositionUpdated(Const.Companion.POSITION_MAP, geo)

            map.clear()

            val option = MarkerOptions()
            with(option) {
                anchor(0.5f, 0.5f)
                position(LatLng(geo.latitude, geo.longitude))
                icon(BitmapDescriptorFactory.fromBitmap(bitmap))
            }

            map.addMarker(option)
        }

    }

    fun locationProvider(): Observable<Location> {
        return Observable.create({ emitter ->
            val geo = Location(LocationManager.GPS_PROVIDER)
            geo.latitude = map.cameraPosition.target.latitude
            geo.longitude = map.cameraPosition.target.longitude

            emitter.onNext(geo)
        })
    }

    fun takeLocationSnapShot(): Location {
        val geoPosition = Location(LocationManager.GPS_PROVIDER)
        geoPosition.latitude = map.cameraPosition.target.latitude
        geoPosition.longitude = map.cameraPosition.target.longitude

        return geoPosition
    }

    private fun bitmapDescriptorFromVector(context: Context, res: Int): Bitmap {
        val vectorDrawable = ContextCompat.getDrawable(context, res) as Drawable
        vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        vectorDrawable.colorFilter =
                PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        vectorDrawable.draw(canvas)

        return bitmap
    }

    interface OnPositionUpdated {
        fun onPositionUpdated(type: Int, location: Location)
    }
}

