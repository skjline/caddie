package com.skjline.caddie.game.fragment

import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment
import com.skjline.caddie.App
import com.skjline.caddie.R
import com.skjline.caddie.common.Base
import com.skjline.caddie.database.StrokeDatabase
import com.skjline.caddie.game.controller.HoleViewController
import com.skjline.caddie.game.controller.MapViewController
import com.skjline.caddie.model.Stroke
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Game : Base() {

    @Inject
    lateinit var locationManager: LocationManager

    @Inject
    lateinit var database: StrokeDatabase

    lateinit var holeController: HoleViewController
    lateinit var mapViewController: MapViewController

    override
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        App.component.inject(this)

        val view = inflater?.inflate(R.layout.game_fragment, container, false) ?: View(context.applicationContext)
        holeController = HoleViewController(view)


        // todo: for testing
        Single.fromCallable<Stroke> {
            val stroke = Stroke(0, 0, false, 0.0, 0.0)
            database.strokeDao().insertStroke(stroke)
            stroke
        }.subscribeOn(Schedulers.io()).concatWith(
                database.strokeDao().getStrokes()
                        .subscribeOn(Schedulers.io())
                        .map { l -> l.last() }
        ).subscribe { s ->
            Log.wtf(Game::class.java.simpleName, "Stroke ID: ${s.strokeId}}")
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val fragment = childFragmentManager
                .findFragmentById(R.id.support_google_map) as SupportMapFragment

        fragment.getMapAsync { googleMap ->
            triggerEvent("map activated")
            mapViewController = MapViewController(googleMap)
            mapViewController.initializeMap(activity, locationManager)
        }

    }


    companion object {
        fun newInstance(bundle: Bundle): Game {
            val game = Game()
            game.arguments = bundle

            return game
        }
    }
}

