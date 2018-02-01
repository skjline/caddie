package com.skjline.caddie.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment
import com.skjline.caddie.R
import com.skjline.caddie.common.Base
import com.skjline.caddie.game.controller.HoleViewController
import com.skjline.caddie.game.controller.MapViewController

class Game : Base() {

    lateinit var holeController: HoleViewController
    lateinit var mapViewController: MapViewController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        holeController = HoleViewController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragment = childFragmentManager
                .findFragmentById(R.id.support_google_map) as SupportMapFragment

        fragment.getMapAsync { googleMap ->
            triggerEvent("map activated")
            mapViewController = MapViewController(googleMap)
            mapViewController.initializeMap(activity!!.applicationContext)

            holeController.presenter = mapViewController
        }
    }

    companion object {
        fun newInstance(bundle: Bundle?): Game {
            val game = Game()

            bundle?.let {
                game.arguments = it
            }
            return game
        }
    }
}

