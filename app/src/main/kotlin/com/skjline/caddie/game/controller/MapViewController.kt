package com.skjline.caddie.game.controller

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
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
import io.reactivex.Single
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

    // todo: poc - need to rework this
    fun onLocationChanged() : Observable<Location> {
        return Observable.create<Location> { emitter ->

            listener = listener ?: object : OnPositionUpdated {
                override fun onPositionUpdated(type: Int, location: Location) {
                    if (emitter.isDisposed) {
                        emitter.onComplete()
                        return
                    }

                    val bundle = Bundle()
                    bundle.putInt(Const.POSITION_TYPE, type)

                    location.extras = bundle
                    emitter.onNext(location)
                }
            }
        }.subscribeOn(Schedulers.io())
    }

    fun takeLocationSnapShot(stroke: Stroke): Single<Stroke> {
        anchor = map.getCameraCenter()

        val s = Stroke(stroke.hole, stroke.stroke, stroke.penalty, anchor?.latitude!!, anchor?.longitude!!)

        return Single.fromCallable {
            database.strokeDao().insertStroke(s)
            s
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

    private interface OnPositionUpdated {
        fun onPositionUpdated(type: Int, location: Location)
    }
}

