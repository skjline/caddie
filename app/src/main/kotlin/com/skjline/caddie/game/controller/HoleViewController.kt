package com.skjline.caddie.game.controller

import android.location.Location
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.skjline.caddie.R
import com.skjline.caddie.common.Const
import com.skjline.caddie.common.ViewController
import com.skjline.caddie.common.utils.bind
import com.skjline.caddie.common.utils.digitFormat
import com.skjline.caddie.common.utils.toLocation

/**
 * Created on 9/9/17.
 */
class HoleViewController(host: View) : ViewController, MapViewController.OnPositionUpdated {

    val tvStroke by host.bind<TextView>(R.id.tv_stroke_count)
    val tvLat by host.bind<TextView>(R.id.tv_latitude)
    val tvLng by host.bind<TextView>(R.id.tv_longitude)

    val tvDistanceNext by host.bind<TextView>(R.id.tv_distance_forward)
    val tvDistancePrev by host.bind<TextView>(R.id.tv_distance_backward)


    val btnStroke by host.bind<Button>(R.id.btn_stroke)
    val btnDrop by host.bind<Button>(R.id.btn_drop)
    val btnHoleOut by host.bind<Button>(R.id.btn_hole_out)

    var ref: Location? = null

    var presenter: MapViewController? = null
        set(value) {
            presenter?.setPositionUpdateListener(this)
        }

    val listener = View.OnClickListener {
        val count = Integer.parseInt(tvStroke.text.toString()) + 1
        tvStroke.text = "$count"

        presenter?.takeLocationSnapShot(count)?.subscribe { s ->
            onPositionUpdated(Const.POSITION_REF, s.toLocation())
        }
    }

    init {

        tvStroke.text = "0"

        btnStroke.setOnClickListener(listener)
        btnDrop.setOnClickListener(listener)
        btnHoleOut.setOnClickListener({
            tvStroke.text = "0"
        })
    }

    override fun onPositionUpdated(type: Int, location: Location) {
        if (type == Const.POSITION_REF) {
            ref = location
        }

        val pos = ref ?: return

        val dist = pos.distanceTo(location).toDouble()

        tvDistanceNext.text = "${dist.digitFormat(2)} meters ${dist.times(Const.METERS_TO_YARDS).digitFormat(2)} yards"
    }
}