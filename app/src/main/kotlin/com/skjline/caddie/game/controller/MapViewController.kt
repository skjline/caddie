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
import com.skjline.caddie.App
import com.skjline.caddie.R
import com.skjline.caddie.common.Const
import com.skjline.caddie.common.ViewController
import com.skjline.caddie.common.model.Stroke
import com.skjline.caddie.common.utils.getCameraCenter
import com.skjline.caddie.database.StrokeDatabase
import com.skjline.caddie.game.presenter.LocationHandler
import com.skjline.caddie.game.presenter.LocationPresenter
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created on 9/15/17.
 */
class MapViewController(private val map: GoogleMap) : ViewController {

    lateinit var bitmap: Bitmap
    lateinit var location: LocationPresenter

    private var anchor: Location? = null
    private var listener: OnPositionUpdated? = null

    @Inject
    lateinit var database: StrokeDatabase
    @Inject
    lateinit var locationManager: LocationManager

    fun initializeMap(context: Context) {
        App.component.inject(this)

        map.setMinZoomPreference(10f)
        map.mapType = GoogleMap.MAP_TYPE_SATELLITE
        map.isMyLocationEnabled = true

        bitmap = bitmapDescriptorFromVector(context, R.drawable.ic_album_black_24dp)

        location = LocationHandler(locationManager)
        location.onPositionChanged().subscribe { loc ->
            anchor = loc
            map.animateCamera(CameraUpdateFactory.newCameraPosition(
                    CameraPosition.Builder()
                            .target(LatLng(loc.latitude, loc.longitude))
                            .zoom(17f)
                            .build()))

            listener?.onPositionUpdated(Const.Companion.POSITION_REF, loc)
        }

        map.setOnCameraMoveListener { setMapCenter() }

    }

    fun setPositionUpdateListener(l: OnPositionUpdated) {
        listener?.let {
            // already set
            Log.d(MapViewController::class.java.simpleName,
                    "Overwriting an existing listener")
        } ?: run { listener = l }
    }

    fun takeLocationSnapShot(count: Int): Observable<Stroke> {
        anchor = map.getCameraCenter()

        return Observable.fromCallable {
            val stroke = Stroke(0, count, false, anchor?.latitude!!, anchor?.longitude!!)
            database.strokeDao().insertStroke(stroke)
            stroke
        }.subscribeOn(Schedulers.io())
    }


    private fun setMapCenter() {
        val geo = map.getCameraCenter()
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

