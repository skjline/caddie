package com.skjline.caddie.game.presenter

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

/**
 * Created on 9/15/17.
 */
class LocationHandler(val lm: LocationManager) : LocationPresenter {

    private var observable: Observable<Location>? = null
    private var location: Location? = null

    override fun onPositionChanged(): Observable<Location> {
        observable = observable ?: Observable.create<Location> { emitter: ObservableEmitter<Location> ->

            val locationChangedListener  = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    emitter.onNext(location)
                }

                override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}

                override fun onProviderEnabled(s: String) {}

                override fun onProviderDisabled(s: String) {}

            }

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5.0f, locationChangedListener)

            emitter.setCancellable { ->
                emitter.onComplete()
                lm.removeUpdates(locationChangedListener)
            }
        }

        location?.let { observable?.startWith(it) }

        return observable ?: throw Exception("illegat state")
    }
}